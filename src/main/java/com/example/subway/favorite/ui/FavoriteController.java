package com.example.subway.favorite.ui;

import com.example.subway.favorite.application.FavoriteService;
import com.example.subway.favorite.application.dto.FavoriteRequest;
import com.example.subway.favorite.application.dto.FavoriteResponse;
import com.example.subway.filter.PreAuthorize;
import com.example.subway.member.application.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/favorites")
    public ResponseEntity<FavoriteResponse> createFavoritePath(@PreAuthorize MemberResponse memberResponse, @RequestBody FavoriteRequest favoriteRequest) {
        FavoriteResponse favoriteResponse = favoriteService.createFavorite(memberResponse, favoriteRequest);
        return ResponseEntity.created(URI.create("/favorites/" + favoriteResponse.getId())).build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteResponse>> findFavorites(@PreAuthorize MemberResponse memberResponse) {
        List<FavoriteResponse> favorites = favoriteService.findFavorites(memberResponse);
        return ResponseEntity.ok().body(favorites);
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<Void> deleteFavorite(@PreAuthorize MemberResponse memberResponse, @PathVariable long id) {
        favoriteService.deleteFavorite(memberResponse, id);
        return ResponseEntity.noContent().build();
    }

}
