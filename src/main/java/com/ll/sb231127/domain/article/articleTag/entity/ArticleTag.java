package com.ll.sb231127.domain.article.articleTag.entity;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class ArticleTag extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article; // 실제로 DB에 저장되는 건 article_id 이다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    private String content;
}
