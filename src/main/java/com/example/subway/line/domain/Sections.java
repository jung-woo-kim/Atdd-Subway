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
        Section newSection = Section.createSection(upStation, downStation, line, distance);
        if(isAddUpSection(upStation)) {

        }
        sections.add(newSection);
    }

    private boolean isAddUpSection(Station upStation) {
        return sections.stream().anyMatch(section -> section.getUpStation().equals(upStation));
    }

    private void addUpSection(Section newSection) {
        Section section = sections.stream().filter(s -> s.getUpStation().equals(newSection.getUpStation())).findFirst().orElseThrow();

    }

    public List<Section> getSections() {
        return sections;
    }
}
