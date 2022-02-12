package com.amber.book.springboot.config.auth;

import com.amber.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring security 설정들을 활성화시킴
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable().headers().frameOptions().disable()//h2-console 화면을 사용하기 위해 disable 해야하는 옵션들
                .and()
                .authorizeRequests() //url별 권한 관리를 설정하는 옵션의 시작점;authorizeRequests가 선언되어야만 andMatchers 옵션 사용 가능
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //andMatchers()->권한 관리 대상 지정
                //permitAll()->이 함수 앞에 지정된 경로들에 대해 전체 열람이 가능하도록 함
                //hasRole(Role.USER.name())->"/api/v1/~"로 들어오는 들어오는 요청에 대해서는 USER 권한을 가진 사람만 가능하도록 함
                .anyRequest() //설정된 url 이외의 다른 url 요청들에 대해
                .authenticated() //인증된 사용자(;로그인한 사용자)에게만 허용됨
                .and()
                .logout() //로그아웃 기능 설정 진입점
                .logoutSuccessUrl("/") //로그아웃 성공 시 / 로 이동
                .and()
                .oauth2Login() //OAuth2 로그인 기능에 대한 설정의 진입점
                .userInfoEndpoint() //OAuth2 로그인 성공 후 사용자 정보를 가져올 때의 설정들 담당
                .userService(customOAuth2UserService); //소셜로그인 성공 시 후 요청에 대한 조치를 진행할 UserService 인터페이스의 구현체 지정
                //CustomOAuth2UserService 클래스에서 리소스 서버(sns)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
    }
}