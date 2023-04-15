package com.example.subway.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @EntityListners
 * AuditingEntityListener 클래스가 callback listener로 지정되어
 * Entity에서 이벤트가 발생할 때마다 특정 로직을 수행하게 된다.
 * AuditingEntityListener내부의 ouchForCreate(), touchForUpdate() 메서드
 *
 * 결과적으로 @CreatedDate와 @LastModifiedDate에 의해 자동으로 업데이트 된다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
