package com.ll.sb231127.global.initData;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.article.service.ArticleService;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    // 개발/테스트 샘플 데이터

    @Autowired
    @Lazy // 본인이 본인 의존성 얻을려다가 뻗는다.
    private NotProd self; // 자신에 대한 객체, 많이 사용하는 패턴
    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    public ApplicationRunner initNotProdData() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.count() > 0) return;

        // 사용자 생성
        Member member1 = memberService.join("user1", "1234").getData();
        Member member2 = memberService.join("user2", "1234").getData();
        Member member3 = memberService.join("user3", "1234").getData();

        // 게시글 생성
        Article article1 = articleService.write(member1.getId(), "제목1", "내용1").getData();
        Article article2 = articleService.write(member1.getId(), "제목2", "내용2").getData();
        Article article3 = articleService.write(member2.getId(), "제목3", "내용3").getData();
        Article article4 = articleService.write(member2.getId(), "제목4", "내용4").getData();

        // 게시글에서 댓글 저장
        article1.addComment(member1, "댓글1");
        article1.addComment(member1, "댓글2");

        article2.addComment(member1, "댓글3");
        article2.addComment(member1, "댓글4");
        article2.addComment(member1, "댓글5");

        article3.addComment(member1, "댓글5");
        article3.addComment(member1, "댓글6");
        article3.addComment(member1, "댓글7");
        article3.addComment(member1, "댓글8");
        article3.addComment(member1, "댓글9");
        article3.addComment(member1, "댓글10");
        article3.addComment(member1, "댓글11");
        article3.addComment(member1, "댓글12");

        // 게시글에서 태그 저장
        article1.addTag("자바");
        article1.addTag("백엔드");
        article2.addTag("프레임워크", "스프링부트");

        article4.addTag("자바", "스프링부트");

        IntStream.rangeClosed(5, 120).forEach(
                i -> {
                    String title = "제목" + i;
                    String body = "내용" + i;

                    articleService.write(member2.getId(), title, body);
                }
        );
    }
}
