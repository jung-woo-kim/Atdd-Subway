package com.example.subway.line.domain;

import com.example.subway.station.domain.Station;
import jakarta.persistence.*;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Station upStation;

    @ManyToOne
    private Station downStation;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    private int distance;

    public Section() {
    }

    public Section(Station upStation, Station downStation, Line line, int distance) {
        this.upStation = upStation;
        this.downStation = downStation;
        this.line = line;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public Line getLine() {
        return line;
    }

    public int getDistance() {
        return distance;
    }

    public static Section createSection(Station upStation, Station downStation, Line line, int distance) {
        return new Section(upStation, downStation, line, distance);
    }
}
