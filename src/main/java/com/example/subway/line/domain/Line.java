package com.example.subway.line.domain;

import com.example.subway.common.BaseEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String color;

    @Embedded
    Sections sections;

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

    public void update(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(getName(), line.getName()) && Objects.equals(getColor(), line.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getColor());
    }
}
