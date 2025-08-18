package com.example.newboard.web.api;

import com.example.newboard.domain.Article;
import com.example.newboard.service.ArticleService;
import com.example.newboard.web.dto.ArticleCreateRequest;
import com.example.newboard.web.dto.ArticleUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")

public class ArticleApiController {
    private final ArticleService articleService;

//    // 응답 상태 코드를 지정
//    @PostMapping
//    public ResponseEntity<Void> create(@Valid @RequestBody ArticleCreateRequest req) { // ResponseEntity 내장함수 :
//        articleService.create(req);
//        return ResponseEntity.status(HttpStatus.CREATED).build();  // 응답 정보
//
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ArticleUpdateRequest req) {
//        articleService.update(id, req);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        articleService.delete(id);
//        return ResponseEntity.noContent().build();  // noContent : 보낼 메세지가 없음, build 비어있음
//
//    }

    @PostMapping
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleCreateRequest req, Authentication auth) {
        Long id = articleService.create(req, auth.getName());
        return ResponseEntity.created(URI.create("/articles/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody ArticleUpdateRequest req,
                                       Authentication auth) {
        articleService.update(id, auth.getName(), req);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        articleService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
