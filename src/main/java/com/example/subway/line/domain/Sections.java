package com.example.subway.line.domain;

import com.example.subway.line.exception.section.SectionDuplicateException;
import com.example.subway.line.exception.section.SectionMinimumSizeException;
import com.example.subway.line.exception.section.SectionNotExistedException;
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
        // 역 사이에 끼는 경우인지 체크
        Optional<Section> upSectionOptional = anyMatchUpStation(section.getUpStation());
        Optional<Section> downSectionOptional = anyMatchDownStation(section.getDownStation());

        validateAddSection(section);

        upSectionOptional.ifPresent(upSection -> changeMatchedUpSection(section, upSection));
        downSectionOptional.ifPresent(downSection -> changeMatchedDownSection(section, downSection));
        sections.add(section);
    }

    private void changeMatchedDownSection(Section section, Section matchedSection) {
        matchedSection.setDownStation(section.getUpStation());
    }

    private void changeMatchedUpSection(Section section, Section matchedSection) {
        matchedSection.setUpStation(section.getDownStation());
    }

    private void validateAddSection(Section section) {
        if (sections.isEmpty()) {
            return;
        }

        boolean hasMatchedUpStation = anyMatchInSections(section.getUpStation());
        boolean hasMatchedDownStation = anyMatchInSections(section.getDownStation());

        if (hasMatchedUpStation && hasMatchedDownStation) {
            throw new SectionDuplicateException();
        }

        if (!hasMatchedDownStation && !hasMatchedUpStation) {
            throw new SectionNotExistedException();
        }

    }

    private boolean anyMatchInSections(Station station) {
        return sections.stream().anyMatch(section -> section.matchAllStation(station));
    }

    private Optional<Section> anyMatchDownStation(Station downStation) {
        return sections.stream().filter(compareSection -> compareSection.matchDownStation(downStation)).findFirst();
    }

    private Optional<Section> anyMatchUpStation(Station upStation) {
        return sections.stream().filter(compareSection -> compareSection.matchUpStation(upStation)).findFirst();
    }

    public List<Station> getStations() {
        List<Station> result = new ArrayList<>();
        Section section = geLastUpSection();
        result.add(section.getUpStation());
        result.add(section.getDownStation());
        Optional<Section> downSection = getNextDownSection(section);

        System.out.println(sections.size());
        System.out.println(downSection.isPresent());
        while (downSection.isPresent()) {
            System.out.println("??");
            section = downSection.get();
            result.add(section.getDownStation());
            downSection = getNextDownSection(section);
        }

        return result;
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

    private Section geLastUpSection() {
        return sections.stream().filter(section -> sections.stream().noneMatch(compareSection -> compareSection.matchDownStation(section.getUpStation()))).findFirst().orElseThrow(SectionMinimumSizeException::new);
    }

    private Section getLastDownSection() {
        return sections.stream().filter(this::isLastSection).findFirst().orElseThrow(SectionMinimumSizeException::new);
    }

    private Optional<Section> getNextDownSection(Section section) {
        return sections.stream().filter(compareSection -> section.matchDownStation(compareSection.getUpStation())).findFirst();
    }

    private boolean isLastSection(Section section) {
        return getNextDownSection(section).isEmpty();
    }

    public List<Section> getSections() {
        return sections;
    }
}

