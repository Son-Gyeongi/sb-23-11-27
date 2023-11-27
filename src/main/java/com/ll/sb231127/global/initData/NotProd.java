package com.ll.sb231127.global.initData;

import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.domain.member.member.service.MemberService;
import com.ll.sb231127.global.rsData.RsData;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Configuration
public class NotProd {
    // 개발/테스트 샘플 데이터
    @Bean
    public ApplicationRunner initNotProdData(
            MemberService memberService
    ) {
        return args -> {
            RsData<Member> joinRs = memberService.join("user1", "1234");

            System.out.println(joinRs.getMsg());
        };
    }
}
