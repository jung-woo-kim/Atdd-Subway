package com.example.subway.line.domain;

import com.example.subway.config.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(length = 20, nullable = false)
    String name;

    @Column(length = 20, nullable = false)
    String color;

    public Line() {
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
