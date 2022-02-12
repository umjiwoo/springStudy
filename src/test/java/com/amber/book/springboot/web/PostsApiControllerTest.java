package com.amber.book.springboot.web;

import com.amber.book.springboot.domain.posts.Posts;
import com.amber.book.springboot.domain.posts.PostsRepository;
import com.amber.book.springboot.web.dto.PostsSaveRequestDto;
import com.amber.book.springboot.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)    //JUnit4 사용 시 @SpringBootTest는 @RunWith(SpringRunner.class)와 함께 사용해야 함
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//호스트가 사용하지 않는 랜덤 포트를 사용하여 요청/응답을 받는 테스트를 진행함
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate; //Rest 방식으로 개발한 API의 테스트를 최적화하기 위해 만들어진 클래스

    @Autowired
    private PostsRepository postsRepository;


    @Autowired
    private WebApplicationContext context;


    //@SpringBootTest에서 MockMvc를 사용하는 과정-1;MockMvc 객체 생성
    private MockMvc mvc;

    @Before //테스트 시작 전 MockMvc 인스턴스를 생성하도록 설정
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER") //인증된 가짜 사용자를 만들어 사용할 것;roles=""로 권한 추가
    //->MockMvc에서만 작동;MockMvc 클래스 객체 만들어주기
    public void register_posts() throws Exception {
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto =
                PostsSaveRequestDto
                        .builder()
                        .title(title)
                        .content(content)
                        .author("author")
                        .build();  //실제 요청 시 컨트롤러에서 DTO 객체를 전달받아 postsServie에서 save()하기 전 toEntity()를 통해 엔티티화 해주는 것처럼 여기서는 바로 엔티티화

        String url = "http://localhost:" + port + "/api/v1/posts";

        //HttpEntity=Http 요청/응답이 이루어질 때 Http 헤더와 바디를 포함하는 클래스
        //RequestEntity,ResponseEntity는 이 HttpEntity를 상속받음
        //postForEntity()->http post 메소드를 요청하는 메소드,ResponseEntity를 반환
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        //@SpringBootTest에서 MockMvc를 사용하는 과정-2;생성된 MockMvc를 이용해 api를 테스트
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk());


        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void update_posts() throws Exception {
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto =
                PostsUpdateRequestDto.builder()
                        .title(expectedTitle)
                        .content(expectedContent)
                        .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//
//        //exchange()->어떤 Http 메서드도 사용가능, 두번째 인자에 사용하고자 하는 Http 메서드 지정
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all=postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
