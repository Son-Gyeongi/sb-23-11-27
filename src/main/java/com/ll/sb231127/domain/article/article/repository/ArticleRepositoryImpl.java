package com.ll.sb231127.domain.article.article.repository;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.articleComment.entity.QArticleComment;
import com.ll.sb231127.domain.article.articleTag.entity.QArticleTag;
import com.ll.sb231127.domain.member.member.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ll.sb231127.domain.article.article.entity.QArticle.article;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {
        // where 조건 입력
        BooleanBuilder builder = new BooleanBuilder();

        QMember author = new QMember("articleAuthor");
        QArticleTag articleTag = new QArticleTag("articleTag");
        QArticleComment comment = new QArticleComment("articleComment");
        QMember commentAuthor = new QMember("articleCommentAuthor");

        if (!kw.isBlank()) {
            // 기존의 조건을 리스트에 담습니다.
            List<BooleanExpression> conditions = new ArrayList<>();

            if (kwTypes.contains("authorUsername")) {
                // where 조건 입력
                conditions.add(author.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("title")) {
                conditions.add(article.title.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("body")) {
                conditions.add(article.body.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("tagContent")) {
                conditions.add(articleTag.content.eq(kw));
            }

            if (kwTypes.contains("commentAuthorUsername")) {
                conditions.add(commentAuthor.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("commentBody")) {
                conditions.add(comment.body.containsIgnoreCase(kw));
            }

            // 조건 리스트를 or 조건으로 결합합니다.
            BooleanExpression combinedCondition = conditions.stream()
                    .reduce(BooleanExpression::or)
                    .orElse(null);

            // 최종적으로 생성된 조건을 쿼리에 적용합니다.
            if (combinedCondition != null) {
                builder.and(combinedCondition);
            }
        }

        JPAQuery<Article> articlesQuery = jpaQueryFactory
                .selectDistinct(article)
                .from(article)
                .leftJoin(article.author, author)
                .leftJoin(article.comments, comment)
                .leftJoin(comment.author, commentAuthor)
                .leftJoin(article.tags, articleTag)
                .where(builder);

        // 정렬 조건
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(article.getType(), article.getMetadata());
            articlesQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        articlesQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = jpaQueryFactory
                .selectDistinct(article.countDistinct())
                .from(article)
                .leftJoin(article.author, author)
                .leftJoin(article.comments, comment)
                .leftJoin(comment.author, commentAuthor)
                .leftJoin(article.tags, articleTag)
                .where(builder);

        return PageableExecutionUtils.getPage(articlesQuery.fetch(), pageable, totalQuery::fetchOne);
    }
}
