package com.example.subway.favorite.application;

import com.example.subway.favorite.application.dto.FavoriteRequest;
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

@Transactional(readOnly = true)
@Service
public class FavoriteService {
    private final MemberRepository memberRepository;
    private final StationRepository stationRepository;

    @Autowired
    public FavoriteService(MemberRepository memberRepository, StationRepository stationRepository) {
        this.memberRepository = memberRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public MemberResponse createFavorite(MemberResponse memberResponse, FavoriteRequest favoriteRequest) {
        Member member = memberRepository.findById(memberResponse.getId()).orElseThrow(MemberNotFoundException::new);
        Station source = stationRepository.findById(favoriteRequest.getSource()).orElseThrow(StationNotFoundException::new);
        Station target = stationRepository.findById(favoriteRequest.getTarget()).orElseThrow(StationNotFoundException::new);

        member.addFavorite(source, target);
        return MemberResponse.of(member);
    }
}
