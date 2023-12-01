package com.ll.sb231127.domain.article.article.service;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.article.repository.ArticleRepository;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public RsData<Article> write(long authorId, String title, String body) {
        Article article = Article.builder()
                .modifyDate(LocalDateTime.now())
                .author(Member.builder().id(authorId).build())
                .title(title)
                .body(body)
                .build();

        articleRepository.save(article);

        return RsData.of("200", "%d번 게시글이 작성되었습니다.".formatted(article.getId()), article);
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public void modify(Article article, String title, String body) {
        article.setTitle(title);
        article.setBody(body);
    }

    // 최근 작성한 게시글부터 모두 출력
    public List<Article> findAll() {
        return articleRepository.findByOrderByIdDesc();
    }

    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
