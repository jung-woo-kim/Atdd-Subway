package com.example.subway.favorite.domain;

import com.example.subway.member.domain.Member;
import com.example.subway.station.domain.Station;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Station source;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Station target;

    public Favorite() {
    }

    private Favorite(Member member, Station source, Station target) {
        this.member = member;
        this.source = source;
        this.target = target;
    }

    public static Favorite createFavorite(Member member, Station source, Station target) {
        return new Favorite(member, source, target);
    }

    public boolean isSameSource(Station source) {
        return source.equals(this.source);
    }

    public boolean isSameTarget(Station target) {
        return target.equals(this.target);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Station getSource() {
        return source;
    }

    public Station getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) {
            return false;
        }

        if (!(o instanceof Favorite favorite)) {
            return false;
        }
        return Objects.equals(getId(), favorite.getId()) && Objects.equals(getMember(), favorite.getMember()) && Objects.equals(getSource(), favorite.getSource()) && Objects.equals(getTarget(), favorite.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMember(), getSource(), getTarget());
    }
}
