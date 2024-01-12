package com.gdsc.colot.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass // JPA에게 이 클래스가 테이블과 매핑되지 않고, 자식 클래스에서만 엔티티로 사용됨
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 상속을 통해 하위 클래스에서만 생성 가능한 기본 생성자
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATE_AT", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateAt;

}
