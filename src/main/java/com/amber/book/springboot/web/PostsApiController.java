package com.amber.book.springboot.web;

import com.amber.book.springboot.service.posts.PostsService;
import com.amber.book.springboot.web.dto.PostsResponseDto;
import com.amber.book.springboot.web.dto.PostsSaveRequestDto;
import com.amber.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")  //해당 경로로 Post 요청이 들어오면 save 함수 실행
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto); //생성한 Service 클래스에서 save() 정의해줄 것
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id,@RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}
