package com.ll.sb231127.domain.article.articleTag.service;

import com.ll.sb231127.domain.article.articleTag.entity.ArticleTag;
import com.ll.sb231127.domain.article.articleTag.repository.ArticleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleTagService {
    private final ArticleTagRepository articleTagRepository;

    // 작성자가 적은 태그 모두 출력
    public List<ArticleTag> findByAuthorId(long authorId) {
        return articleTagRepository.findByArticle_authorId(authorId);
    }
}
