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

    public Favorite addFavorite(Favorite favorite) {
        validateFavorite(favorite);
        favorites.add(favorite);
        return favorite;
    }

    public void deleteFavorite(Favorite favorite) {
        validateDeleteFavorite(favorite);
        favorites.remove(favorite);
    }

    public void validateDeleteFavorite(Favorite favorite) {
        if (favorites.stream().noneMatch(inFavorite -> inFavorite.equals(favorite))) {
            throw new IllegalArgumentException("멤버가 해당 즐겨찾기를 갖고있지 않습니다.");
        }
    }

    private void validateFavorite(Favorite favorite) {
        if (favorites.stream().anyMatch(f -> f.isSameSource(favorite.getSource()) && f.isSameTarget(f.getTarget()))) {
            throw new IllegalArgumentException("즐겨찾기 중복");
        }
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }
}
