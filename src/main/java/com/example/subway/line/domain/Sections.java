package com.example.subway.line.domain;

import com.example.subway.station.domain.Station;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    List<Section> stations;

    public void addSection(Station upStation, Station downStation, Line line, int distance) {
        stations.add(Section.createSection(upStation, downStation, line, distance));
    }
}
