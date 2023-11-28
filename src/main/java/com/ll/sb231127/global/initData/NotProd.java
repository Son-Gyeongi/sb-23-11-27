package com.ll.sb231127.global.initData;

import com.ll.sb231127.domain.article.article.entity.Article;
import com.ll.sb231127.domain.article.article.service.ArticleService;
import com.ll.sb231127.domain.article.articleComment.service.ArticleCommentService;
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
    private final ArticleCommentService articleCommentService;

    @Bean
    public ApplicationRunner initNotProdData() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        // 사용자 생성
        Member member1 = memberService.join("user1", "1234").getData();
        Member member2 = memberService.join("user2", "1234").getData();

        // 게시글 생성
        Article article1 = articleService.write(member1.getId(), "제목1", "내용1").getData();
        Article article2 = articleService.write(member1.getId(), "제목2", "내용2").getData();
        Article article3 = articleService.write(member2.getId(), "제목3", "내용3").getData();
        Article article4 = articleService.write(member2.getId(), "제목4", "내용4").getData();
    }

    @Transactional
    public void work2() {
        Member member1 = memberService.findById(1L).get();
        Article article1 = articleService.findById(1L).get();

        // 게시글에서 댓글 저장
        articleCommentService.write(member1, article1, "댓글1");
        articleCommentService.write(member1, article1, "댓글1");
    }
}
