package com.amber.book.springboot.web.domain.posts;

//JpaRepository 인터페이스를 상속받음으로써 생성된 CRUD 메소드 테스트
//save(),findOne(),findAll(),deleteAll()...
import com.amber.book.springboot.domain.posts.Posts;
import com.amber.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest //실제 프로그램을 로컬에 올려 포트 주소가 listen 되고, 실제 데이터베이스와 커넥션이 이뤄지는 상태에서 진행되는 테스트
//@SpringBootTest 사용 시 H2테이터베이스 자동 실행
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After //단위 테스트가 끝날 때마다 수행되는 메소드 지정
    public void cleanUp(){
        postsRepository.deleteAll();
    }

    @Test
    public void retrieve_posts(){
        //save() 테스트->id 값이 없을 때-insert,id 값이 있을 때-update 쿼리 실행
        String title="테스트 게시글";
        String content="테스트 본문";

        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("jiwoo123@gmail.com")
                        .build() //엔티티클래스.builder().~~.build()통해 객체에 값 채우기
        ); //값 채운 후 새로 받은 값(혹은 변경된 값)으로 데이터베이스에 저장(혹은 수정)

        //findAll() 테스트
        List<Posts> postsList=postsRepository.findAll(); //posts 테이블에 있는 모든 데이터 조회해오기
        Posts posts=postsList.get(0); //그중 첫번째 데이터 선택
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
