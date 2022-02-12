package com.amber.book.springboot.config.auth;

import com.amber.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { //
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        //getParameterAnnotation()->들어온 파라미터에 @LoginUser 어노테이션이 붙어있으면 @LoginUser 어노테이션 반환;없으면 null 반환
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        //들어온 파라미터의 클래스 타입이 SessionUser와 같으면 true 리턴
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {//파라미터에 전달할 객체를 생성하는 함수
        return httpSession.getAttribute("user"); //여기서는 생성하는 대신 세션에 저장되어 있는 user 라는 이름의 속성을 가져와 넘김
    }
}
