package com.amber.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor //아무런 인자도 갖지 않는 생성자 생성
@Entity //테이블과 링크될(매칭될) 클래스임을 나타냄-JPA 사용 시 데이터베이스 작업 시 실제 쿼리를 날리는 대신 Entity 클래스 수정을 통해 작업함
public class Posts {
    @Id //해당 테이블의 PK 필드 지정
    @GeneratedValue(strategy= GenerationType.IDENTITY) //PK 생성 규칙 지정;GenerationType.IDENTITY=auto_increment 형식
    private Long id;

    @Column(length=500,nullable=false) //@Column=해당 필드가 테이블의 컬럼이 될 것이라 선언
    private String title;

    @Column(columnDefinition = "TEXT",nullable=false) //기본값 외에 변경이 필요한 옵션이 있을 때 사용
    private String content;

    private String author; // 이와 같이 @Column을 선언해주지 않아도 해당 클래스의 필드는 모두 컬럼이 됨

    @Builder // 어노테이션 선언으로 인해 해당 클래스의 빌더 패턴 클래스가 자동으로 생성됨(생성자에 포함된 필드만 빌더에 포함됨)
    public Posts(String title,String content,String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public void update(String title,String content){ //Setter 메소드를 생성하는 대신 값 변경을 위한 메소드 명시적으로 생성
        this.title=title;
        this.content=content;
    }
}
