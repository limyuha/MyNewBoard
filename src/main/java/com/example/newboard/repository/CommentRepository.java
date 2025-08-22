package com.example.newboard.repository;


import com.example.newboard.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 게시글 ID(articleId)로 댓글 조회
    @EntityGraph(attributePaths = {"author"})  // author까지 한 번에 같이 select
    List<Comment> findByArticleId(Long articleId);
}