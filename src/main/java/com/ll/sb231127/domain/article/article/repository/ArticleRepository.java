package com.ll.sb231127.domain.article.article.repository;

import com.ll.sb231127.domain.article.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByOrderByIdDesc();

    // 순수 JPA 메서드로 검색구현, 구현한 조건들 : 전체 조건 체크
    Page<Article> findByAuthor_usernameContainingOrTitleContainingOrBodyContainingOrTags_contentOrComments_author_usernameContainingOrComments_bodyContaining(String kw, String kw_, String kw__, String kw___, String kw____, String kw_____, Pageable pageable);
    // 순수 JPA 메서드로 검색구현, 구현한 조건들 : 제목, 내용, 제목 or 내용
    Page<Article> findByTitleContainingOrBodyContaining(String kw, String kw_, Pageable pageable);
    Page<Article> findByTitleContaining(String kw, Pageable pageable);
    Page<Article> findByBodyContaining(String kw, Pageable pageable);
    // 순수 JPA 메서드로 검색구현, 구현한 조건들 : 작성자명 or 제목 or 내용
    Page<Article> findByAuthor_usernameContainingOrTitleContainingOrBodyContaining(String kw, String kw_, String kw__, Pageable pageable);
    Page<Article> findByAuthor_usernameContaining(String kw, Pageable pageable);
}
