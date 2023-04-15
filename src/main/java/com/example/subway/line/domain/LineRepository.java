package com.example.subway.line.domain;

import com.example.subway.line.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line,Long> {
    Line findByName(String name);
}
