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
import java.util.Optional;

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
        Optional<Section> section = getFirstSection();

        if (section.isEmpty()) {
            return result;
        }
        Section tempSection = section.get();
        result.add(tempSection.getUpStation());

        while (section.isPresent()) {
            tempSection = section.get();
            result.add(tempSection.getDownStation());
            section = getNextSection(tempSection);
        }

        return result;
    }

    private void validationSectionStation(Section section) {
        if (sections.isEmpty()) {
            return;
        }
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
        return sections.stream().noneMatch(section -> section.matchUpStation(station));
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

    private Optional<Section> getFirstSection() {
        return sections.stream().filter(section -> sections.stream().noneMatch(compareSection -> compareSection.matchDownStation(section.getUpStation()))).findFirst();
    }

    private Optional<Section> getNextSection(Section section) {
        return sections.stream().filter(compareSection -> section.matchDownStation(compareSection.getUpStation())).findFirst();
    }

    public List<Section> getSections() {
        return sections;
    }
}

