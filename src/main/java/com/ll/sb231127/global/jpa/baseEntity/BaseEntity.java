package com.ll.sb231127.global.jpa.baseEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

// 모든 엔티티가 가져야할 공통 속성을 모아두는 클래스 BaseEntity 도입
@MappedSuperclass // 부모 클래스로 사용되는 것을 나타낸다, 매핑 정보 상속
@SuperBuilder // 부모 클래스인 BaseEntity에서도 빌더 패턴을 사용할 수 있다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수가 없는 protected 접근 제한자를 가지는 기본 생성자를 자동으로 생성
@Getter // 필드에 대한 getter 메서드가 자동으로 생성
@EqualsAndHashCode // equals()와 hashCode() 메서드를 자동으로 생성
@ToString // toString() 메서드를 자동으로 생성
public class BaseEntity {
    @Id // 엔티티의 식별자(primary key)로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 엔티티의 식별자 값을 자동으로 생성
    @EqualsAndHashCode.Include // 이 어노테이션이 적용된 필드는 equals()와 hashCode() 메서드에서 동등성 비교에 사용
    private Long id;
}
