package com.example.subway.line.domain;

import com.example.subway.station.domain.Station;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Station downStation;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    private int distance;

    protected Section() {
    }

    private Section(Station upStation, Station downStation, Line line, int distance) {
        this.upStation = upStation;
        this.downStation = downStation;
        this.line = line;
        this.distance = distance;
    }

    public boolean matchUpStation(Station upStation) {
        return upStation.equals(this.upStation);
    }

    public boolean matchDownStation(Station downStation) {
        return downStation.equals(this.downStation);
    }

    public void setUpStation(Station upStation) {
        this.upStation = upStation;
    }

    public void setDownStation(Station downStation) {
        this.downStation = downStation;
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

    public boolean matchAllStation(Station station) {
        return upStation.equals(station) || downStation.equals(station);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return getDistance() == section.getDistance() && Objects.equals(getId(), section.getId()) && Objects.equals(getUpStation(), section.getUpStation()) && Objects.equals(getDownStation(), section.getDownStation()) && Objects.equals(getLine(), section.getLine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUpStation(), getDownStation(), getLine(), getDistance());
    }


}
