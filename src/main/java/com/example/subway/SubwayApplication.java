package com.example.subway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @EnableJpaAuditing
 * JPA Auditing(감시, 감사) 기능을 활성화하기 위한 어노테이션
 * BaseTime Entity의 createdDate, modifiedDate를 자동으로 변경 시켜준다.
 */
@SpringBootApplication
@EnableJpaAuditing
public class SubwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubwayApplication.class, args);
    }

}
