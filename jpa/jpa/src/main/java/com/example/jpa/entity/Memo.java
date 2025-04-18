package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter;
import lombok.ToString;

// db기준 예제
// 번호(mno), 내용(memo_text length200), 생성날짜(created_date), 수정날짜(updated_date)
// mno 자동증가, pk
// 그 외 나머지 칼럼들은 NN(Not Null)

@EntityListeners(value = AuditingEntityListener.class)

@Getter
// @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
    @Column(length = 200, nullable = false)
    private String memoText;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public void changeMemoText(String memoText) {
        this.memoText = memoText;
    }
}
