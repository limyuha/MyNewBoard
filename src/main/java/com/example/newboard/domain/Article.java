package com.example.newboard.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Article {  // 테이블과 매핑되는 그릇
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)  // N:1 (여러 글 → 한 사용자)
    @JoinColumn(name = "author_id", nullable = false)  // 외래키 컬럼명
    private User author;  // User 테이블의 id와 연결되는 외래키 컬럼, User 객체를 반환(롬복의 @getter으로 반환메서드 없어도 됨)
    // article.getAuthor() 호출 시 User 객체를 반환

    // ✅ 작성일 추가
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 엔티티가 저장될 때 자동으로 시간 넣기
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
