package com.example.subway.favorite.ui;

import com.example.subway.favorite.application.FavoriteService;
import com.example.subway.favorite.application.dto.FavoriteRequest;
import com.example.subway.filter.PreAuthorize;
import com.example.subway.member.application.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/favorites")
    private ResponseEntity<MemberResponse> createFavoritePath(@PreAuthorize MemberResponse memberResponse, @RequestBody FavoriteRequest favoriteRequest) {
        MemberResponse member = favoriteService.createFavorite(memberResponse, favoriteRequest);
        return ResponseEntity.created(URI.create("/favorites/" + member.getId())).build();
    }
}
