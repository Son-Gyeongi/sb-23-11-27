package com.ll.sb231127.domain.article.articleComment.service;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.articleComment.entity.ArticleComment;
import com.ll.sb231127.domain.article.articleComment.repository.ArticleCommentRepository;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional
    public RsData<ArticleComment> write(Member author, Article article, String body) {
        ArticleComment articleComment = ArticleComment.builder()
                .author(author)
                .article(article)
                .body(body)
                .build();

        articleCommentRepository.save(articleComment);

        return RsData.of("200", "%d번 댓글이 작성되었습니다.".formatted(articleComment.getId()), articleComment);
    }

    public Optional<ArticleComment> findLatest() {
        return articleCommentRepository.findFirstByOrderByIdDesc();
    }

    @Transactional
    public void modify(ArticleComment comment, String body) {
        comment.setBody(body);
    }

    public Optional<ArticleComment> findFirstByArticleIdOrderByIdDesc(long l) {
        return articleCommentRepository.findFirstByArticleIdOrderByIdDesc(l);
    }

    public void delete(ArticleComment comment) {
        articleCommentRepository.delete(comment);
    }
}
