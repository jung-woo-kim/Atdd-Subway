package com.example.subway.config;

import com.example.subway.filter.AuthMemberHandlerArgumentResolver;
import com.example.subway.filter.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthMemberHandlerArgumentResolver authMemberHandlerArgumentResolver;

    public WebConfig(AuthenticationInterceptor authenticationInterceptor, AuthMemberHandlerArgumentResolver authMemberHandlerArgumentResolver) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.authMemberHandlerArgumentResolver = authMemberHandlerArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/members/me")
                .addPathPatterns("/favorites/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authMemberHandlerArgumentResolver);
    }
}