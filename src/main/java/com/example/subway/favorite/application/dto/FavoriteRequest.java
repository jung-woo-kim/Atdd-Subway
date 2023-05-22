package com.example.subway.favorite.application.dto;

public class FavoriteRequest {
    long source;
    long target;

    public FavoriteRequest(long source, long target) {
        this.source = source;
        this.target = target;
    }

    public FavoriteRequest() {
    }

    public long getSource() {
        return source;
    }

    public long getTarget() {
        return target;
    }
}
