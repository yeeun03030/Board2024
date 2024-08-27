package kr.ac.kopo.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 다른 Entity에 상속되게 사용할 것이기에 테이블로 만들지 않음
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity { // 추상 클래스

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate; // 등록한 날짜 및 시간

    @LastModifiedDate // 마지막에 수정된 날짜 및 시간을 가져옴
    @Column(name = "modDate") // 갱신되어야 하기에 'updatable = true'
    private LocalDateTime modDate; // 수정한 날짜 및 시간
}
