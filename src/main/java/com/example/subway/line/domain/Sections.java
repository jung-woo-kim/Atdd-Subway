package com.example.subway.line.domain;

import com.example.subway.line.exception.section.SectionDuplicateException;
import com.example.subway.line.exception.section.SectionMinimumSizeException;
import com.example.subway.line.exception.section.SectionNotLastStationException;
import com.example.subway.station.domain.Station;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Sections {
    private static final int MINIMUM_SIZE = 1;

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections= new ArrayList<>();

    public void addSection(Section section) {
        validationSectionStation(section);

        sections.add(section);
    }

    public List<Station> getStations() {
        List<Station> result = new ArrayList<>();
        if (sections.size() > 0) {
            sections.forEach(sec
                    -> result.add(sec.getUpStation()));
            result.add(sections.get(sections.size() - 1).getDownStation());
        }
        return result;
    }

    private void validationSectionStation(Section section) {
        if (!matchDownStation(section.getUpStation())) {
            throw new SectionNotLastStationException();
        }

        if (matchAllStation(section.getDownStation())) {
            throw new SectionDuplicateException();
        }
    }

    public void deleteStation(Station station) {
        validationDeleteStation(station);
        Section lastSection = sections.get(lastIndex());
        sections.remove(lastSection);
    }

    private void validationDeleteStation(Station station) {
        if (!isLastDownStation(station)) {
            throw new SectionNotLastStationException();
        }

        if (isMinimumSectionSize()) {
            throw new SectionMinimumSizeException();
        }
    }

    private boolean isMinimumSectionSize() {
        return sections.size() == MINIMUM_SIZE;
    }

    private boolean isLastDownStation(Station station) {
        return sections.get(lastIndex()).getDownStation().equals(station);
    }

    private int lastIndex() {
        return sections.size() - 1;
    }

    private boolean matchDownStation(Station station) {
        return sections.size() == 0
                || isLastDownStation(station);
    }

    private boolean matchAllStation(Station station) {
        return sections.stream()
                .anyMatch(sec -> sec.matchAllStation(station));
    }

    public List<Section> getSections() {
        return sections;
    }
}

