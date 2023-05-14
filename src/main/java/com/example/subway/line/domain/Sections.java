package com.example.subway.line.domain;


import com.example.subway.line.exception.section.*;

import com.example.subway.station.domain.Station;
import com.example.subway.station.exception.StationExceptionType;
import com.example.subway.station.exception.StationNotFoundException;
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
    private List<Section> sections = new ArrayList<>();


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

        validateMatchedSection(section, matchedSection);
        matchedSection.setDownStation(section.getUpStation());
        matchedSection.setDistance(matchedSection.getDistance() - section.getDistance());
    }

    private void validateMatchedSection(Section section, Section matchedSection) {
        if (section.getDistance() >= matchedSection.getDistance()) {
            throw new SectionDistanceExceedException();
        }
    }

    private void changeMatchedUpSection(Section section, Section matchedSection) {
        validateMatchedSection(section, matchedSection);
        matchedSection.setUpStation(section.getDownStation());
        matchedSection.setDistance(matchedSection.getDistance() - section.getDistance());
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

        while (downSection.isPresent()) {
            section = downSection.get();
            result.add(section.getDownStation());
            downSection = getNextDownSection(section);
        }

        return result;
    }

    public void deleteStation(Station station) {
        Optional<Section> upSectionOptional = anyMatchDownStation(station);
        Optional<Section> downSectionOptional = anyMatchUpStation(station);

        validateDeleteStation(upSectionOptional.isPresent(), downSectionOptional.isPresent());

        if (upSectionOptional.isPresent() && downSectionOptional.isPresent()) {
            Section upSection = upSectionOptional.get();
            Section downSection = downSectionOptional.get();
            upSection.setDistance(downSection.getDistance() + upSection.getDistance());
            upSection.setDownStation(downSection.getDownStation());
            sections.remove(downSection);
            return;
        }

        if (upSectionOptional.isPresent()) {
            Section upSection = upSectionOptional.get();
            sections.remove(upSection);
            return;
        }

        if (downSectionOptional.isPresent()) {
            Section downSection = downSectionOptional.get();
            sections.remove(downSection);
        }

    }

    private void validateDeleteStation(boolean isMatchUpSection, boolean isMatchDownSection) {
        if (isMinimumSectionSize()) {
            throw new SectionMinimumSizeException();
        }

        if (!isMatchDownSection && !isMatchUpSection) {
            throw new StationNotFoundException();
        }
    }

    private boolean isMinimumSectionSize() {
        return sections.size() == MINIMUM_SIZE;
    }

    private Section geLastUpSection() {
        return sections.stream().filter(section -> sections.stream().noneMatch(compareSection -> compareSection.matchDownStation(section.getUpStation()))).findFirst().orElseThrow(SectionMinimumSizeException::new);
    }

    private Optional<Section> getNextDownSection(Section section) {
        return sections.stream().filter(compareSection -> section.matchDownStation(compareSection.getUpStation())).findFirst();
    }

    public List<Section> getSections() {
        return sections;
    }
}

