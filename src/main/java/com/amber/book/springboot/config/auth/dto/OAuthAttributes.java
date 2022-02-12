package com.amber.book.springboot.config.auth.dto;

//OAuth2UserService를 통해 가져온 OAuth2User의 속성을 담을 클래스

import com.amber.book.springboot.domain.user.Role;
import com.amber.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,String userNameAttributeName,Map<String,Object> attributes){
        return ofGoogle(userNameAttributeName,attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes){ //전달받은 속성이 Map 형태이므로 값 하나하나 변환해줘야함
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
