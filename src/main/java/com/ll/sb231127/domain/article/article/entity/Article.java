package com.ll.sb231127.domain.article.article.entity;

import com.ll.sb231127.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne
    private Member author; // 작가
    private String title; // 게시글 제목
    private String body; // 게시글 내용
}
