package com.amber.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication //스프링부트 자동 설정,Bean 읽기/생성 자동으로 생성해줌 !!프로젝트 최상단 클래스에 있어야함
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
        //SpringApplication.run->내장 WAS 실행시킴
    }
}
