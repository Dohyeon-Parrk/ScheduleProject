package com.sparta.scheduledevelope.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedule")
/* 
    - JPA Auditing 활성화
    1. @CreatedDate 와 @LastModifiedDate 작동시키기 위한 JPA Auditing 기능 활성화하는 어노테이션 
        -> @EntityListeners(AuditingEntityListener.class)
    2. 메인 클래스에 @EnableJpaAuditing 추가
 */
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일정은 이제 작성 유저명 필드 대신 유저 고유 식별자 필드를 가집니다.
    // -> username 은 PK 가 아니기때문에 user 테이블의 user_id 를 FK 로 설정하여 사용
//    @Column(nullable = false, unique = true)
//    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    // 일정 하나는 여러개의 댓글 가짐 -> OneToMany
    // cascade = CascadeType.ALL -> 일정에 변경이 있을 때 연관된 댓글에도 적용 -> 일정 삭제 시 달려 있던 댓글도 같이 삭제됨.
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 유저 한명은 여러개의 일정을 가짐 -> ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
