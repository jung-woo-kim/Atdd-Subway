package com.example.subway.favorite.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class Favorites {

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Favorite> favorites;

    public Favorites() {
    }

    public Favorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);

    }

    public List<Favorite> getFavorites() {
        return favorites;
    }
}
