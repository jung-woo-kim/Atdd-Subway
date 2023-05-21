package com.example.subway.favorite.application.dto;

import com.example.subway.favorite.domain.Favorite;
import com.example.subway.station.domain.Station;

public class FavoriteResponse {
    private long id;
    private Station source;
    private Station target;

    public FavoriteResponse(long id, Station source, Station target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public long getId() {
        return id;
    }

    public Station getSource() {
        return source;
    }

    public Station getTarget() {
        return target;
    }

    public static FavoriteResponse of(Favorite favorite) {
        return new FavoriteResponse(favorite.getId(), favorite.getSource(), favorite.getTarget());
    }
}
