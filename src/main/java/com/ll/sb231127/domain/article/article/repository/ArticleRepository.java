package com.ll.sb231127.domain.article.article.repository;

import com.ll.sb231127.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    // ArticleRepository 에 QueryDSL 적용 - 적용했다는 뜻은 ArticleRepositoryCustom, ArticleRepositoryImpl 클래스 추가

    List<Article> findByOrderByIdDesc();
}
