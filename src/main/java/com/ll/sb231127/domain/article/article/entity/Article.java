package com.ll.sb231127.domain.article.article.entity;

import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity // 해당 클래스가 엔티티임을 의미
@SuperBuilder //  빌더 패턴을 자동으로 생성해주는 기능, 부모 클래스인 BaseEntity에서도 빌더 패턴을 사용 가능
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter // 모든 필드에 대한 setter 메서드를 자동으로 생성
@Getter // 모든 필드에 대한 getter 메서드를 자동으로 생성
@ToString(callSuper = true) // callSuper = true를 설정하여 부모 클래스인 BaseEntity의 toString() 메서드도 포함
public class Article extends BaseEntity {
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY) // @ManyToOne(fetch = LAZY) 를 통해서 데이터를 필요한 만큼만 FETCH
    private Member author; // 작가
    private String title; // 게시글 제목
    private String body; // 게시글 내용
}
