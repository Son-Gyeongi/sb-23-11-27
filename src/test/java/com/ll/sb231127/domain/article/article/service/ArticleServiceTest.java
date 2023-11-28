package com.ll.sb231127.domain.article.article.service;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.articleComment.entity.ArticleComment;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.domain.member.member.service.MemberService;
import com.ll.sb231127.global.rsData.RsData;
import com.ll.sb231127.standard.util.Ut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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

    @DisplayName("1번 글을 가져온다.")
    @Test
    void t2() {
        // 1번 글 찾기
        Article article = articleService.findById(1L).get();

        // 검증
        assertThat(article.getTitle()).isEqualTo("제목1");
    }

    @DisplayName("1번 글의 작성자의 username은 user1이다.")
    @Test
    void t3() {
        // @ManyToOne(fetch = LAZY) 를 통해서 데이터를 필요한 만큼만 FETCH.
        // 추가로 필요한 데이터가 있으면 자동 FETCH

        // 1번 글 찾기
        Article article = articleService.findById(1L).get();
        // 1번 글에서 작성자 찾기
        Member author = article.getAuthor();

        // 검증
        assertThat(author.getUsername()).isEqualTo("user1");
    }

    @DisplayName("1번 글의 제목을 수정한다.")
    @Test
    void t4() {
        Article article = articleService.findById(1L).get();

        Ut.thread.sleep(1000); // 1초 sleep

        articleService.modify(article, "수정된 제목!!", "수정된 내용!!");

        Article article_ = articleService.findById(1L).get();

        assertThat(article_.getTitle()).isEqualTo("수정된 제목!!");
    }

    @DisplayName("2번 글에 댓글들을 추가한다.")
    @Test
    @Rollback(value = false)
    void t5() {
        Member member1 = memberService.findById(1L).get();
        Article article2 = articleService.findById(2L).get();

        article2.addComment(member1, "댓글 입니다.");
    }

    @DisplayName("1번 글의 댓글들을 수정한다.")
    @Test
    void t6() {
        Article article = articleService.findById(1L).get();

        article.getComments().forEach(
                comment -> {
                    articleService.modifyComment(comment, comment.getBody() + "!!");
                }
        );
    }

    @DisplayName("1번 글의 댓글 중 마지막 것을 삭제한다.")
    @Test
    void t7() {
        Article article = articleService.findById(1L).get();

        ArticleComment lastComment = article.getComments().getLast();

        article.removeComment(lastComment);
    }
}
