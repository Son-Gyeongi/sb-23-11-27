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
    @ManyToOne(fetch = FetchType.LAZY) // @ManyToOne(fetch = LAZY) 를 통해서 데이터를 필요한 만큼만 FETCH
    private Member author; // 작가
    private String title; // 게시글 제목
    private String body; // 게시글 내용
}
