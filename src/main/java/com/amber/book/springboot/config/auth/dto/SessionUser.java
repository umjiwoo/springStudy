package com.amber.book.springboot.config.auth.dto;

import com.amber.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

//세션에 사용자 정보를 저장하기 위한 Dto 클래스;User 클래스는 다른 엔티티와의 관계 형성 가능성이 있어 직렬화를 구현하지 않는 것이 운영에 효율적이므로 직렬화 기능을 가진 Dto를 추가로 만드는 것이 좋음
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name=user.getName();
        this.email=user.getEmail();
        this.picture=user.getPicture();
    }
}
