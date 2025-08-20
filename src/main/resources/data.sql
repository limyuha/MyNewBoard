-- User 더미 데이터
INSERT INTO users (id, email, password, name, role)
VALUES (1, 'test@example.com', '1234', '테스트유저', 'USER');

-- Article 더미 데이터 (created_at 추가!)
INSERT INTO article (id, title, content, author_id, created_at)
--VALUES (1, '테스트 글 1', '첫 번째 내용입니다.', 1, CURRENT_TIMESTAMP);
VALUES (1, '테스트 글 1', '첫 번째 내용입니다.', 1, '2025-08-20 15:00:00');

INSERT INTO article (id, title, content, author_id, created_at)
VALUES (2, '테스트 글 2', '두 번째 내용입니다.', 1, '2025-08-20 15:30:00');

INSERT INTO article (id, title, content, author_id, created_at)
VALUES (3, '테스트 글 3', '세 번째 내용입니다.', 1, CURRENT_TIMESTAMP);

INSERT INTO article (id, title, content, author_id, created_at)
VALUES (4, '테스트 글 4', '네 번째 내용입니다.', 1, CURRENT_TIMESTAMP);

INSERT INTO article (id, title, content, author_id, created_at)
VALUES (5, '테스트 글 5', '다섯 번째 내용입니다.', 1, CURRENT_TIMESTAMP);

INSERT INTO article (id, title, content, author_id, created_at)
VALUES (6, '테스트 글 6', '여섯 번째 내용입니다.', 1, CURRENT_TIMESTAMP);

