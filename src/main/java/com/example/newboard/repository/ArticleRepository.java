package com.example.newboard.repository;

import com.example.newboard.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//public interface ArticleRepository extends JpaRepository<Article, Long> {
//    Optional<Article> findByIdAndAuthor_Email(Long id, String email);
//    long deleteByIdAndAuthor_Email(Long id, String email);
//}

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @EntityGraph(attributePaths = "author")
    Optional<Article> findById(Long id);

    @EntityGraph(attributePaths = "author")
    Optional<Article> findByIdAndAuthor_Email(Long id, String email);

    long deleteByIdAndAuthor_Email(Long id, String email);

    // 최신순 정렬, 페이징
    @EntityGraph(attributePaths = "author")
    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);
}


// CRUD 제공
// findAll()
