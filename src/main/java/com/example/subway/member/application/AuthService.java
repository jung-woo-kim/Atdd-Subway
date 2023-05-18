package com.example.subway.member.application;

import com.example.subway.member.application.dto.GithubProfileResponse;
import com.example.subway.member.application.dto.TokenRequest;
import com.example.subway.member.application.dto.TokenResponse;
import com.example.subway.member.domain.Member;
import com.example.subway.member.domain.MemberRepository;
import com.example.subway.member.exception.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final GithubClient githubClient;

    @Autowired
    public AuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, GithubClient githubClient) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.githubClient = githubClient;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = memberRepository.findByEmail(tokenRequest.getEmail())
                .orElseThrow(MemberNotFoundException::new);
        validateMemberPassword(tokenRequest, member);
        return TokenResponse.of(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()));
    }

    public TokenResponse loginWithGithub(String code) {
        String accessToken = githubClient.getAccessTokenFromGithub(code);
        GithubProfileResponse profileResponse = githubClient.getGithubProfileFromGithub(accessToken);
        Member member = memberRepository.findByEmail(profileResponse.getEmail())
                .orElse(memberRepository.save(new Member(profileResponse.getEmail(), "password", 20)));
        return TokenResponse.of(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()));
    }


    private void validateMemberPassword(TokenRequest tokenRequest, Member member) {
        if (!member.checkPassword(tokenRequest.getPassword())) {
            throw new MemberNotFoundException();
        }
    }
}
