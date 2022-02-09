package com.amber.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//DB 접근할 접근자-인터페이스 생성 후 JpaRepository<Entity 클래스,PK 타입> 인터페이스를 상속받으면 기본적인 CRUD 메소드가 자동으로 생성됨
//Entity 클래스와 기본 Repository 인터페이스는 같은 패키지 내에 있어야 함
public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  //Spring Data Jpa에서 제공하지 않는 메소드는 직접 정의해서 사용해도 됨
    List<Posts> findAllDesc();
}
