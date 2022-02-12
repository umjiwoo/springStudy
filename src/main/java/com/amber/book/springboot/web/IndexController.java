package com.amber.book.springboot.web;

import com.amber.book.springboot.config.auth.LoginUser;
import com.amber.book.springboot.config.auth.dto.SessionUser;
import com.amber.book.springboot.service.posts.PostsService;
import com.amber.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model,@LoginUser SessionUser user){ //Model->서버 템플릿 엔진에서 사용할 수 있는 객체 저장
        model.addAttribute("posts",postsService.findAllDesc()); //postsService.findAllDesc()로 가져온 결과를 posts라는 이름으로 index.mustache에 전달

        //SessionUser user=(SessionUser) httpSession.getAttribute("user");
        // 인증 후 setAttribute(attributes)로 저장해놨던 user라는 이름의 세션 가져오기
        //->@LoginUser 어노테이션 클래스 생성 후 해당 코드 삭제 가능해짐;@LoginUser를 사용하면 세션 정보를 가져올 수 있게 됨

        if(user != null){
            model.addAttribute("userName",user.getName());
        }

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
