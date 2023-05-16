package com.example.subway.filter;

import com.example.subway.member.application.dto.MemberResponse;
import com.example.subway.member.exception.MemberNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthMemberHandlerArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PreAuthorize.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        MemberResponse member = (MemberResponse) request.getAttribute("member");
        PreAuthorize preAuthorize = parameter.getParameterAnnotation(PreAuthorize.class);
        if (preAuthorize == null || member == null) {
            throw new MemberNotFoundException();
        }
        return member;
    }
}
