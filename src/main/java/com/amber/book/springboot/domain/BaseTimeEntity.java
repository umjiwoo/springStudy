package com.amber.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //BaseTimeEntity를 상속받는 모든 클래스들의 필드에 BaseTimeEntity의 필드를 추가하고 컬럼으로 인식되게 함
@EntityListeners(AuditingEntityListener.class) //BaseTimeEntity 클래스에 auditing 기능을 포함시킴
public class BaseTimeEntity {
    @CreatedDate //엔티티가 생성되어 저장될 때 시간이 자동으로 저장되게 함
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 엔티티의 값 변경 시 시간이 자동으로 저장되게 함
    private LocalDateTime modifiedDate;
}
