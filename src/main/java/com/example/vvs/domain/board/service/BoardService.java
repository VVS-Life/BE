package com.example.vvs.domain.board.service;

import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.board.dto.BoardResponseDTO;
import com.example.vvs.domain.board.entity.Board;
import com.example.vvs.domain.board.repository.BoardRepository;
import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.common.s3.S3Service;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import com.example.vvs.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.vvs.exception.ErrorHandling.*;


@Service // 스프링빈 등록
@RequiredArgsConstructor // 의존성 주입(final에 대해)
@Transactional(readOnly = true) // CRUD에서 R은 대부분의 메소드에 사용하기에 클래스 단위에서 권한을 주고, 메소드 단위에서 CUD 기능이 필요하면 거기서 권한을 주도록.
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;


    @Transactional
    public ResponseEntity<MessageDTO> createBoard(BoardRequestDTO boardRequestDTO,
                                                  List<MultipartFile> multipartFileList,
                                                  Long memberId) throws IOException {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        if (multipartFileList.isEmpty()) {
            boardRepository.save(Board.builder()
                    .boardRequestDTO(boardRequestDTO)
                    .image(null)
                    .member(member)
                    .build());
            //Diary diary = diaryRepository.save(Diary.of(diaryRequestDto, null, member));
            return new ResponseEntity<>(MessageDTO.builder()
                    .message("게시글 등록 성공")
                    .statusCode(HttpStatus.OK.value())
                    .build(), null, HttpStatus.OK);
        }

//        for (MultipartFile multipartFile : multipartFileList) {
//            // 파일첨부를 하지 않은 경우 - img를 null로 입력하여 저장.
//            if (multipartFile.getOriginalFilename().equals("")) {
//                boardRepository.save(Board.builder()
//                        .boardRequestDTO(boardRequestDTO)
//                        .image(null)
//                        .member(member)
//                        .build());
//                //Diary diary = diaryRepository.save(Diary.of(diaryRequestDto, null, member));
//                return new ResponseEntity<>(MessageDTO.builder()
//                        .message("게시글 등록 성공")
//                        .statusCode(HttpStatus.OK.value())
//                        .build(), null, HttpStatus.OK);
//            }
//        }

        s3Service.uploadBoard(multipartFileList, boardRequestDTO, member);

        String uploadImageUrl = s3Service.getUploadImageUrl();


        // 이거 condition 설정 왜함?
        /*DiaryCondition condition = PUBLIC;

        if (diaryRequestDto.getDiaryCondition().equals(PRIVATE)) {
            condition = PRIVATE;
        }*/

        boardRepository.save(Board.builder()
                .boardRequestDTO(boardRequestDTO)
                .image(uploadImageUrl)
                .member(member)
                .build());

        return ResponseEntity.ok(MessageDTO.builder()
                .message("게시글 등록 성공")
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    public ResponseEntity<Page<BoardResponseDTO>> findAllBoard(PageRequest pageRequest) {
        Page<BoardResponseDTO> boards = boardRepository.findAllByOrderByIdDesc(pageRequest)
                .map((Board board) -> BoardResponseDTO.builder().board(board).build());

        return ResponseEntity.ok(boards);
    }

    public ResponseEntity<BoardResponseDTO> findBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(
                        NullPointerException::new
                );

        BoardResponseDTO boardResponseDTO = BoardResponseDTO.builder().board(board).build();

        return ResponseEntity.ok(boardResponseDTO); // ResponseEntity<BoardResponseDTO> 타입
    }

    // TODO: 2023/10/14 세션 처리 필요
    @Transactional
    public ResponseEntity<MessageDTO> updateBoard(BoardRequestDTO boardRequestDTO, Long boardId, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ApiException(NOT_FOUND_BOARD_ID)
        );

        if (!board.getMember().getId().equals(findMember.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZTION); // ErrorHandling.NOT_MATCH_AUTHORIZTION 로 에러 처리.
        }

        board.update(boardRequestDTO);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("게시글 수정 성공")
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @Transactional // 진짜 까먹지 말고 쓰자.
    public ResponseEntity<MessageDTO> deleteBoard(Long boardId, Long memberId) {
        // 작성자 본인만 삭제 가능
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(NOT_MATCH_USER)
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ApiException(NOT_FOUND_BOARD_ID)
        );

        // S3 업로드 파일 삭제
        if (board.getImage() != null) {
            String uploadPath = board.getImage(); // "url1, url2,,," 형식으로 구성.
            String[] fileNames = uploadPath.split(","); //콤마로 구분하여 각각 삭제시켜줘야함.
            for (String fileName : fileNames) { // 파일 하나씩 삭제
                s3Service.deleteFile(fileName.substring(57)); // 삭제할 때 url이 아닌 name을 줘야하기 때문에 url 앞의 57글자를 삭제시켜 이후의 name만 가져다 쓰도록 substring을 사용.
                System.out.println(fileName.substring(57));
            }
        }

        // TODO: 2023/10/14  작성자 객체 대신 일단 id만
        if (!board.getMember().getId().equals(findMember.getId()) || !findMember.getRole().equals("ADMIN")) {
            throw new ApiException(NOT_MATCH_AUTHORIZTION); // ErrorHandling.NOT_MATCH_AUTHORIZTION 로 에러 처리.
        }

        // 삭제
        //boardRepository.deleteById(id); //id로 삭제.
        boardRepository.delete(board);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("게시글 삭제 성공")
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
