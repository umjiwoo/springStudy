package com.amber.book.springboot.config.auth;

import com.amber.book.springboot.config.auth.dto.OAuthAttributes;
import com.amber.book.springboot.config.auth.dto.SessionUser;
import com.amber.book.springboot.domain.user.User;
import com.amber.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> { //OAuth2UserService 인터페이스를 구현하는 CustomOAuth2UserService 클래스
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate=new DefaultOAuth2UserService();
        //직접 구현한 CustomOAuth2UserService 외에 자바에서 구현해놓은 DefaultOAuth2UserService 클래스를 이용해 OAuth2UserService 구현한 객체 delegate
        OAuth2User oAuth2User=delegate.loadUser(userRequest); //해당 객체의 loadUser() 호출해 OAuth2User로 받음

        String registrationId=userRequest.getClientRegistration()
                .getRegistrationId(); //로그인 진행중인 서비스(구글,네이버...)를 구분하는 코드 받아옴
        String userNameAttributeName=userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();//OAuth2 로그인 진행 시 유니크 키가 되는 필드 값을 불러옴
        //구글->sub=1  네이버->id=1 ...

        //OAuthAttributes 클래스:OAuth2UserService를 통해 가져온 OAuth2User의 속성을 담을 클래스
        OAuthAttributes attributes=OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());
        //로그인 진행중인 서비스 정보, 로그인 진행중인 사용자의 유니크 키,  로그인 진행중인 사용자의 속성 정보를 of()로 넘김
        // of(~.ofGoogle())을 통해서 oAuth2User.getAttributes()의 반환값인 Map형태의 정보를 하나하나 변환하여 엔티티화

        User user=saveOrUpdate(attributes); //전달받은 속성들에 의해 User 정보 저장 or 업데이트
        httpSession.setAttribute("user",new SessionUser(user)); //정보 저장(수정) 후 사용자 정보를 저장한 객체를 httpSession에 올림

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),attributes.getAttributes(),attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes){
        User user=userRepository.findByEmail(attributes.getEmail())
                .map(entity->entity.update(attributes.getName(),attributes.getPicture())) //객체 존재 시 정보 변경 여부와 상관 없이 일단 update() 호출
                .orElse(attributes.toEntity()); //객체 없으면 toEntity() 호출하여 엔티티화

        return userRepository.save(user); //save()를 통해 저장 또는 업데이트
    }
}
