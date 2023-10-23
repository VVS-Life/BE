package com.example.vvs.domain.reply.repository;

import com.example.vvs.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByBoardIdOrderByIdDesc(Long boardId);
}
