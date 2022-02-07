package com.amber.book.springboot.web;

import com.amber.book.springboot.domain.posts.Posts;
import com.amber.book.springboot.domain.posts.PostsRepository;
import com.amber.book.springboot.web.dto.PostsSaveRequestDto;
import com.amber.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void register_posts() {
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
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
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

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //exchange()->어떤 Http 메서드도 사용가능, 두번째 인자에 사용하고자 하는 Http 메서드 지정
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all=postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
