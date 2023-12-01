package com.ll.sb231127.domain.article.article.entity;

import com.ll.sb231127.domain.article.articleComment.entity.ArticleComment;
import com.ll.sb231127.domain.article.articleTag.entity.ArticleTag;
import com.ll.sb231127.domain.member.member.entity.Member;
import com.ll.sb231127.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity // 해당 클래스가 엔티티임을 의미
@SuperBuilder //  빌더 패턴을 자동으로 생성해주는 기능, 부모 클래스인 BaseEntity에서도 빌더 패턴을 사용 가능
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter // 모든 필드에 대한 setter 메서드를 자동으로 생성
@Getter // 모든 필드에 대한 getter 메서드를 자동으로 생성
@ToString(callSuper = true) // callSuper = true를 설정하여 부모 클래스인 BaseEntity의 toString() 메서드도 포함
public class Article extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) // @ManyToOne(fetch = LAZY) 를 통해서 데이터를 필요한 만큼만 FETCH
    private Member author; // 작가 / 실제로 DB에 저장되는 건 author_id 이다.
    private String title; // 게시글 제목
    private String body; // 게시글 내용

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true) // 필드와 연결이 끊어진 고아 데이터
    @Builder.Default // builder 할 때 comments 필드는 null이 아닌 new ArrayList<>();로 고정
    @ToString.Exclude // OneToMany 필드들에 @ToString.Exclude 추가하여 재귀적인 무한호출 방지
    private List<ArticleComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true) // @OneToMany는 기볹적으로 LAZY타입
    @Builder.Default // build할 때 값이 없는 필드의 경우 null이 들어오는데 해당 어노테이션을 사용하면 null이 아닌 주어진 값을 그대로 쓴다.
    @ToString.Exclude
    private List<ArticleTag> tags = new ArrayList<>();

    // Article에서 댓글 저장
    public void addComment(Member commentAuthor, String commentBody) {
        ArticleComment comment = ArticleComment.builder()
                .article(this)
                .author(commentAuthor)
                .body(commentBody)
                .build();

        comments.add(comment);
    }

    public void removeComment(ArticleComment comment) {
        comments.remove(comment);
    }

    // Article에서 태그 저장
    public void addTag(String tagContent) {
        ArticleTag tag = ArticleTag.builder()
                .article(this)
                .author(author)
                .content(tagContent)
                .build();

        tags.add(tag);
    }

    // 여러 개의 태그가 매개변수로 들어온다면
    public void addTag(String... tagContents) {
        for (String tagContent : tagContents) {
            addTag(tagContent);
        }
    }

    // 태그만 가져오기
    public String getTagsStr() {
        String tagsStr = tags
                .stream()
                .map(ArticleTag::getContent)
                .collect(Collectors.joining(" #"));

        if (tagsStr.isBlank()) {
            return "";
        }

        return "#" + tagsStr;
    }
}
