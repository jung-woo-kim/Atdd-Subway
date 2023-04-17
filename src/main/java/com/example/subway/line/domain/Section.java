package com.example.subway.line.domain;

import com.example.subway.station.domain.Station;
import jakarta.persistence.*;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Station preStation;

    @ManyToOne
    private Station station;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;
}
