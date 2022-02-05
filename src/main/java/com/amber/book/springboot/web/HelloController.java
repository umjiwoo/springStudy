package com.amber.book.springboot.web;


import com.amber.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //해당 컨트롤러를 Json을 반환하는 컨트롤러화
public class HelloController {

    @GetMapping("/hello") // ~/hello url로 get 요청이 들어오면 처리해줌
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount)// @RequestParam->외부에서 api로 넘긴 파라미터를 가져옴
    {
        return new HelloResponseDto(name,amount);
    }
}
