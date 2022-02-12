package com.amber.book.springboot.web;

import com.amber.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class) //스프링부트 테스트와 JUnit 사이의 연결자 역할,인자로 선언한 클래스의 실행자를 실행시킴
@WebMvcTest(controllers=HelloController.class
        ,excludeFilters={@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,classes= SecurityConfig.class)}) //컨트롤러 단(API 호출 부분)만 테스트하는 방법-전체 프로그램 실행x, Controller 만 로드하여 테스트
//@Controller,@ControllerAdvice 등은 클래스 내부에서 사용 가능(@Service,@Repository...는 사용 불가)
//SecurityConfig를 생성하기위해 필요한 CustomOAuth2UserService는 읽을 수 없으므로 SecurityConfig 파일은 스캔 대상에서 제거
public class HelloControllerTest {
    @Autowired //스프링이 관리하는 빈 주입받음;생성자 따로 호출 필요x
    private MockMvc mvc; //웹 api를 사용할 때 사용하는 클래스

    @WithMockUser(roles="USER")
    @Test //테스트 코드의 시작을 알리는 어노테이션
    public void return_hello() throws Exception{
        String hello="hello";
        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
        //mvc.perform(http메소드("경로"))->MockMvc를 통해 주어진 경로로 http요청 전달
        //.andExpect(~~) [1]  1) status()->mvc.perform의 결과 내보냄 2) .isOk()->상태 검증(200일 때 true)
        //               [2]  1) content()->응답 본문 내보냄  2) .string()->응답 본문의 내용이 인자로 들어오는 값이랑 같은지 비교
    }

    @WithMockUser(roles="USER")
    @Test
    public void return_helloDto() throws Exception{
        String name="jiwoo";
        int amount=1000;

        mvc.perform(get("/hello/dto")
                        .param("name",name) //요청 경로에 있는 path-variable 중 name 이라는 이름을 갖는 파라미터에 name객체(즉,jiwoo)를 넣겠다
                        .param("amount",String.valueOf(amount))) //param()에 들어오는 값은 String만 허용됨
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount))); //jsonPath()->json 응답값을 필드별로 검증할 수 있게해줌,$를 기준으로 필드명 명시해주기
    }
}
