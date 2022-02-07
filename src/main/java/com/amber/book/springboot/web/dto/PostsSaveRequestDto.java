package com.amber.book.springboot.web.dto;
// 기존 Entity 클래스와 코드 내용은 매우 유사하나 Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스로 이를 기준으로 테이블이 생성되고 스키마가 변경됨.
// 반면 요청이나 응답용 DTO는 뷰를 위한 클래스이기 때문에 수정이 자주 일어남, 또한 요청 종류에 따라 DTO 클래스는 여러개 생성되지만 Entity 클래스는 단일임.
// 따라서 DTO 클래스와 Entity 클래스의 역할 분리 해줘야함

import com.amber.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder //빌더 패턴 클래스 자동 생성되도록 함
    public PostsSaveRequestDto(String title,String content,String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();   //전달받은 DTO 객체를 데이터베이스에 저장될 엔티티 객체로 바꿔줌
    }
}
