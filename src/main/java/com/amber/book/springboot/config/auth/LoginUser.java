package com.amber.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //해당 어노테이션이 생성될 수 있는 위치 지정;ElementType.PARAMETER->메소드의 파라미터로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME) //어노테이션이 언제까지 살아 남아 있을지를 정함
public @interface LoginUser {//@interface->해당 파일을 어노테이션 클래스로 지정할 것을 명시
}
