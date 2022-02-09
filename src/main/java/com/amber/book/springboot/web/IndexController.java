package com.amber.book.springboot.web;

import com.amber.book.springboot.service.posts.PostsService;
import com.amber.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.Entity;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){ //Model->서버 템플릿 엔진에서 사용할 수 있는 객체 저장
        model.addAttribute("posts",postsService.findAllDesc()); //postsService.findAllDesc()로 가져온 결과를 posts라는 이름으로 index.mustache에 전달
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,Model model){
        PostsResponseDto dto=postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }
}
