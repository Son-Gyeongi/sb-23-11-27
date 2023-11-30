package com.ll.sb231127.domain.article.article.service;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.articleComment.entity.ArticleComment;
import com.ll.sb231127.domain.article.articleComment.service.ArticleCommentService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ArticleCommentService articleCommentService;

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

        article2.addComment(member1, "댓글3");
    }

    @DisplayName("1번 글의 댓글들을 수정한다.")
    @Test
    void t6() {
        Article article1 = articleService.findById(1L).get();

        article1.getComments().getLast().setBody("수정된 댓글");
    }

    @DisplayName("1번 글의 댓글 중 마지막 것을 삭제한다.")
    @Test
    void t7() {
        Article article1 = articleService.findById(1L).get();

        ArticleComment lastComment = article1.getComments().getLast();

        article1.removeComment(lastComment);
    }

    @DisplayName("게시물 별 댓글 수 출력")
    @Test
    void t8() {
        List<Article> articles = articleService.findAll();

        // 여기서 N + 1 문제가 발생
        articles.forEach(article -> {
            System.out.println("게시물 번호: " + article.getId());
            System.out.println("댓글 수: " + article.getComments().size());
        });
    }

    @DisplayName("1번 게시물의 태그(String)를 반환한다.")
    @Test
    void t9() {
        Article article1 = articleService.findById(1L).get();

        String tagsStr = article1.getTagsStr();

        assertThat(tagsStr).isEqualTo("#자바 #백엔드");
    }

    @DisplayName("1번 게시물 toString")
    @Test
    void t10() {
        Article article1 = articleService.findById(1L).get();

        System.out.println("article 객체 : " + article1);
    }

    @DisplayName("1번 회원이 작성한 댓글들")
    @Test
    void t11() {
        List<ArticleComment> articleComments = articleCommentService.findByAuthorId(1L);

        assertThat(articleComments.size()).isGreaterThan(0);
    }
}
