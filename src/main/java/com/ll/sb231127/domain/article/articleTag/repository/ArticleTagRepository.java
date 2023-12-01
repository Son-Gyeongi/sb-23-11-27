package com.ll.sb231127.domain.article.articleTag.repository;

import com.ll.sb231127.domain.article.articleTag.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    // 태그를 찾는 건 ArticleTag에 저장된 article_id를 가지고 Article id와 같은 부분에서 authorId가 (예시)1번인 경우를 찾는다.
    // Article이랑 조인을 해서 authorId 로  작성자를 찾아서 태그들을 보여준다.
    List<ArticleTag> findByArticle_authorId(long authorId);

    // ArticleTag에서 Article을 조인한다. 그리고 Member(author)도 조인한다. username이 (예시)user1인 회원인 글들의 태그를 찾는다.
    List<ArticleTag> findByArticle_author_username(String username);
}
