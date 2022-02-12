package com.amber.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> { //<Entity 클래스,PK 타입>
    Optional<User> findByEmail(String email); //소셜 로그인으로 반환되는 값 중 이메일을 통해 이미 생성된 사용자인지 체크
    //Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스;참조하더라도 NPE가 발생하지 않도록 도와줌
}
