package com.nexus.config;

import com.nexus.api.FirebaseAPI;
import com.nexus.api.FirebaseAPIConfig;
import com.nexus.exception.AuthorizationException;
import com.nexus.model.FirebaseReq;
import com.nexus.model.FirebaseRes;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;


@Component
public class AuthenticationInterceptor implements AsyncHandlerInterceptor {

    @Autowired
    @Lazy
    private FirebaseAPI firebaseAPI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("In Authentication Interceptor");
        checkUserAuthority();
        return true;
    }

    private void checkUserAuthority() throws Exception {
        String token = FirebaseAPIConfig.getBearerTokenHeader();
        token = token.replace("Bearer ", "");
        try {
            FirebaseReq req = new FirebaseReq(token);
            FirebaseRes user = firebaseAPI.tokenVerify(req, "AIzaSyCjxgT9pk782PE1E50yp93OVeUb7aeGnmw");
        } catch (FeignException.FeignClientException | FeignException.FeignServerException ex) {
            System.out.println(ex.getMessage());
            throw new AuthorizationException(ex.getMessage());
        }


    }

}
