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
        // JPA 방식(객체)방식으로 코드가 짧아진다.
        // step 4, article 객체에 memberId 가 아니라 member 자체를 저장하는 방식으로 변경(즉 DB 방식이 아니라 객체 방식으로 변경)

        // 1번 글 찾기
        Article article = articleService.findById(1L).get();
        // 1번 글에서 작성자 찾기
        Member author = article.getAuthor();

        // 검증
        Assertions.assertThat(author.getUsername()).isEqualTo("user1");
    }
}
