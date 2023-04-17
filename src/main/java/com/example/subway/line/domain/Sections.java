package com.example.subway.line.domain;

import com.example.subway.station.domain.Station;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    List<Section> sections= new ArrayList<>();

    public void addSection(Station upStation, Station downStation, Line line, int distance) {
        sections.add(Section.createSection(upStation, downStation, line, distance));
    }

    public List<Section> getSections() {
        return sections;
    }
}
