package com.example.subway.member.application;

import com.example.subway.member.application.dto.TokenRequest;
import com.example.subway.member.application.dto.TokenResponse;
import com.example.subway.member.domain.Member;
import com.example.subway.member.domain.MemberRepository;
import com.example.subway.member.exception.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = memberRepository.findByEmail(tokenRequest.getEmail())
                .orElseThrow(MemberNotFoundException::new);
        validateMemberPassword(tokenRequest, member);
        return TokenResponse.of(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()));
    }

    private void validateMemberPassword(TokenRequest tokenRequest, Member member) {
        if (!member.checkPassword(tokenRequest.getPassword())) {
            throw new MemberNotFoundException();
        }
    }
}
