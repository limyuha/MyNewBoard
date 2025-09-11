package com.example.newboard.config;

import com.example.newboard.service.security.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing  // JPA Auditing 기능 켜기, JPA가 자동으로 넣어주도록 함(날짜)
public class SecurityConfig {
    private final CustomOidcUserService customOidcUserService;

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    // → 비밀번호 해시용 빈. 회원가입/로그인 검증 시 사용

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/", "/articles", "/articles/**", "/login", "/join", "/css/**", "/js/**").permitAll()
    // → 위 경로들은 누구나 접근 가능(비로그인 허용)
                                // 댓글 조회는 비로그인도 허용
                                .requestMatchers(HttpMethod.GET, "/api/articles/*/comments/**").permitAll()
                                // 그 외 /api/** (POST/DELETE 등)는 로그인 필요
                                .requestMatchers("/api/**").authenticated()
    // → /api/** 요청은 인증 필요 (CUD 보호 목적)
                                .anyRequest().permitAll()
    // → 그 외 나머지는 일단 허용
                )  // 로그인
                .formLogin(form -> form
                        .loginPage("/login")            // 로그인 페이지 URL (GET) -> /login GET 요청을 처리하는 View 컨트롤러 필요
                        .loginProcessingUrl("/login")   // 로그인 폼 전송 POST URL
                        .defaultSuccessUrl("/articles", true)  // 로그인 성공 시 이동
                        .permitAll()
                )
    // → 폼 로그인 설정. 컨트롤러에서 직접 처리 안 해도 Spring Security가 인증 처리 -> 즉 @PostMapping("/login") 필요없음
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/articles")  // 로그인 성공 후 강제로 /articles로 이동
                )

                .oauth2Login(oauth -> oauth
                        .loginPage("/login")  // 구글 로그인 실패/필요 시 보여줄 커스텀 로그인 페이지
                        .userInfoEndpoint(u -> u
                                .oidcUserService(customOidcUserService)  // 사용자 정보 매핑 커스터마이징
                        )
                )

    // → 세션 무효화/쿠키 삭제 등 기본 로그아웃 동작 수행
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );



    // → CSRF 보호 활성화(예외 없음). 토큰을 쿠키/헤더로 주고받음
        return http.build();
    }
}