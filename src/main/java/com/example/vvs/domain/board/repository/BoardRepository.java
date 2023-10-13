package com.example.vvs.domain.board.repository;

import com.example.vvs.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByOrderByIdDesc(Pageable pageable);

}
