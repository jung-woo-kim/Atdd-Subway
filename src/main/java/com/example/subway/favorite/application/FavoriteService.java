package com.example.subway.favorite.application;

import com.example.subway.favorite.application.dto.FavoriteRequest;
import com.example.subway.favorite.application.dto.FavoriteResponse;
import com.example.subway.favorite.domain.Favorite;
import com.example.subway.favorite.domain.FavoriteRepository;
import com.example.subway.member.application.dto.MemberResponse;
import com.example.subway.member.domain.Member;
import com.example.subway.member.domain.MemberRepository;
import com.example.subway.member.exception.MemberNotFoundException;
import com.example.subway.station.domain.Station;
import com.example.subway.station.domain.StationRepository;
import com.example.subway.station.exception.StationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class FavoriteService {
    private final MemberRepository memberRepository;
    private final StationRepository stationRepository;
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteService(MemberRepository memberRepository, StationRepository stationRepository, FavoriteRepository favoriteRepository) {
        this.memberRepository = memberRepository;
        this.stationRepository = stationRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional
    public FavoriteResponse createFavorite(MemberResponse memberResponse, FavoriteRequest favoriteRequest) {
        Member member = memberRepository.findById(memberResponse.getId()).orElseThrow(MemberNotFoundException::new);
        Station source = stationRepository.findById(favoriteRequest.getSource()).orElseThrow(StationNotFoundException::new);
        Station target = stationRepository.findById(favoriteRequest.getTarget()).orElseThrow(StationNotFoundException::new);

        Favorite favorite = member.addFavorite(source, target);
        Favorite savedFavorite = favoriteRepository.save(favorite);
        return FavoriteResponse.of(savedFavorite);
    }

    public List<FavoriteResponse> findFavorites(MemberResponse memberResponse) {
        Member member = memberRepository.findById(memberResponse.getId()).orElseThrow(MemberNotFoundException::new);
        return member.getFavorites().stream()
                .map(FavoriteResponse::of)
                .toList();
    }

    @Transactional
    public void deleteFavorite(MemberResponse memberResponse, long id) {
        Member member = memberRepository.findById(memberResponse.getId()).orElseThrow(MemberNotFoundException::new);
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        member.deleteFavorite(favorite);
    }
}
