package com.amber.book.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void lombok_function_test(){
        String name="test";
        int amount=1000;

        HelloResponseDto dto=new HelloResponseDto(name,amount);
        //final로 선언된 두개의 필드를 인자로 갖는 생성자가 자동으로 생성되었는지 확인하기 위한 코드;테스트가 통과한다면 자동으로 생성된 것
        assertThat(dto.getName()).isEqualTo(name); //assertThat()->검증하고 싶은 대상을 메소드 인자로 받음
        assertThat(dto.getAmount()).isEqualTo(amount);
        //생성한 HelloResponseDto 객체의 get메소드를 호출해봄으로써 @Get 어노테이션이 작용했는지 확인
    }
}
