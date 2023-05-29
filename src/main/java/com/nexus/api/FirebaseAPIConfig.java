package com.nexus.api;

import com.nexus.exception.UnHandledCustomException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FirebaseAPIConfig  {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getBearerTokenHeader() {
      String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AUTHORIZATION_HEADER);
        if(token != null){
            return token;
        } else {
            throw new UnHandledCustomException("Token is required to access application resources.");
        }
    }



}
