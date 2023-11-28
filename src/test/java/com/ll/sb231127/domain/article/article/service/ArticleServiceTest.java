package com.ll.sb231127.domain.article.article.service;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.domain.member.member.service.MemberService;
import com.ll.sb231127.global.rsData.RsData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberService memberService;

    @DisplayName("글 쓰기")
    @Test
    void t1() {
        RsData<Article> writeRs = articleService.write(1, "제목", "내용");
        Article article = writeRs.getData();

        assertThat(article.getId()).isGreaterThan(0L);
    }

    @DisplayName("1번 글의 작성자의 username은 user1이다.")
    @Test
    void t2() {
        // JPA 방식(객체)방식이 아니기 때문에 코드가 길다.

        // 1번 글 찾기
        Article article = articleService.findById(1L).get();
        // 1번 글에서 작성자 id 찾기
        long authorId = article.getAuthorId();

        Member member = memberService.findById(authorId).get();

        // 검증
        Assertions.assertThat(member.getUsername()).isEqualTo("user1");
    }
}
