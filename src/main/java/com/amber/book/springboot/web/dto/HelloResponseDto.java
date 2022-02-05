package com.amber.book.springboot.web.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Getter //해당 클래스에서 선언된 모든 필드의 get메소드 생성해줌
@RequiredArgsConstructor //해당 클래스에서 final로 선언된 필드들이 포함된 생성자를 자동으로 생성해줌
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
