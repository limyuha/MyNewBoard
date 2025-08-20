package com.example.newboard.service;

import com.example.newboard.domain.Article;
import com.example.newboard.repository.ArticleRepository;
import com.example.newboard.repository.UserRepository;
import com.example.newboard.web.dto.ArticleCreateRequest;
import com.example.newboard.web.dto.ArticleUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // 전체 목록 (기존)
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    // 페이징 + 최신순 (추가)
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public Long create(ArticleCreateRequest req, String email){
        var author = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return articleRepository.save(
                Article.builder()
                        .title(req.getTitle())
                        .content(req.getContent())
                        .author(author)
                        .build()
        ).getId();
    }

    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found:" + id));
    }

    @Transactional
    public void update(Long id, String email, ArticleUpdateRequest req){
        var article = articleRepository.findByIdAndAuthor_Email(id, email)
                .orElseThrow(() -> new AccessDeniedException("본인 글이 아닙니다."));

        article.update(req.getTitle(), req.getContent());
    }

    @Transactional
    public void delete(Long id, String email){

        if (articleRepository.deleteByIdAndAuthor_Email(id, email) == 0)
            throw new AccessDeniedException("본인 글이 아닙니다.");
    }

//    @Transactional
//    public void create(ArticleCreateRequest request) {
//        articleRepository.save(  // save를 이미 가지고 있음, 3. 필드 세팅된 데이터들을 db에 저장해줌
//                Article.builder()  // 1. Article은 @Builder로 생성되어 있고 정적 메서드라서 클래스 이름으로 호출한다. 실제 객체는 build()에서 생성된다.
//                        .title(request.getTitle())  // 2. request에 저장된 데이터를 꺼내서 db랑 연동된 그릇에 데이터를 넣어줌(필드 세팅)
//                        .content(request.getContent())
//                        .build()
//        );
//    }

//    @Transactional
//    public void update(Long id, ArticleUpdateRequest req) {
//        var article = findById(id);
//        article.update(req.getTitle(), req.getContent());
//    }
//    @Transactional
//    public void delete(Long id) {
//        articleRepository.deleteById(id);
//    }
}
