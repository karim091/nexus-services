package com.nexus.api;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FirebaseAPIConfig  {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AUTHORIZATION_HEADER);
    }

    @Bean(name = "firebase-client")
    RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(AUTHORIZATION_HEADER, getBearerTokenHeader());
    }

}
