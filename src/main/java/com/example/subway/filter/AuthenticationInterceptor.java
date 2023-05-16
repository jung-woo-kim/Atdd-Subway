package com.example.subway.filter;

import com.example.subway.filter.exception.AccessTokenExceptionType;
import com.example.subway.filter.exception.InvalidAccessTokenException;
import com.example.subway.member.application.JwtTokenProvider;
import com.example.subway.member.application.MemberService;
import com.example.subway.member.application.dto.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

import static com.example.subway.filter.exception.AccessTokenExceptionType.*;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final String BEARER = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateBearerToken(response, bearerToken);

        String accessToken = bearerToken.substring(BEARER.length());
        validateAccessToken(response, accessToken);

        String principal = jwtTokenProvider.getPrincipal(accessToken);
        MemberResponse member = memberService.findMemberByEmail(principal);
        request.setAttribute("member", member);
        return true;
    }

    private void validateAccessToken(HttpServletResponse response, String accessToken) throws IOException {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            throw new InvalidAccessTokenException(INVALID_ACCESS_TOKEN);
        }
    }

    private void validateBearerToken(HttpServletResponse response, String bearerToken) throws IOException {
        if (bearerToken == null || !bearerToken.startsWith(BEARER)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            throw new InvalidAccessTokenException(INVALID_ACCESS_TOKEN);
        }
    }
}
