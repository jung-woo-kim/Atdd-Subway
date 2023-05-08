package com.example.subway.line.domain;

import com.example.subway.line.exception.section.SectionDuplicateException;
import com.example.subway.line.exception.section.SectionExceptionType;
import com.example.subway.line.exception.section.SectionNotExistedException;
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
        boolean addUpSection = isAddUpSection(upStation);
        boolean addDownSection = isAddDownSection(downStation);

        validateSection(addUpSection, addDownSection);

        if(addUpSection) {
            // (A B) (B D) -> + (A C) -> (A C) (C B) (B D)
            //TODO : 상행 구간의 상행역을 downStation 으로 변경
            addUpSection(newSection);
            return;
        }

        if(addDownSection) {
            // (A B) (B D) -> + (C B) -> (A C) (C B) (B D)
            //TODO : 상행 구간의 하행역을 upStation 으로 변경
            addDownSection(newSection);
            return;
        }
        sections.add(newSection);
    }

    private boolean isAddDownSection(Station downStation) {
        return sections.stream().anyMatch(section -> section.matchDownStation(downStation));
    }

    private boolean isAddUpSection(Station upStation) {
        return sections.stream().anyMatch(section -> section.matchUpStation(upStation));
    }

    private void addUpSection(Section newSection) {
        Section section = sections.stream().filter(s -> s.matchUpStation(newSection.getUpStation())).findFirst().orElseThrow();
        section.setUpStation(newSection.getDownStation());
        sections.add(newSection);
    }

    private void addDownSection(Section newSection) {
        Section section = sections.stream().filter(s -> s.matchDownStation(newSection.getUpStation())).findFirst().orElseThrow();
        section.setDownStation(newSection.getUpStation());
        sections.add(newSection);
    }

    private void validateSection(boolean addUpSection, boolean addDownSection) {
        if (sections.isEmpty()) {
            return;
        }

        if (addUpSection && addDownSection) {
            throw new SectionDuplicateException(SectionExceptionType.SECTION_DUPLICATE);
        }

        if (!addUpSection && ! addDownSection) {
            throw new SectionNotExistedException(SectionExceptionType.SECTION_NOT_EXIST);
        }
    }

    public List<Section> getSections() {
        return sections;
    }
}
