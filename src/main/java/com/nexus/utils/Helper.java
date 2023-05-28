package com.nexus.utils;

import com.nexus.api.FirebaseAPI;
import com.nexus.api.FirebaseAPIConfig;
import com.nexus.exception.AuthorizationException;
import com.nexus.model.FirebaseReq;
import com.nexus.model.FirebaseRes;
import com.nexus.model.UserRole;
import com.nexus.model.Users;
import com.nexus.services.IUserService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Helper {


    @Autowired
    @Lazy
    private FirebaseAPI firebaseAPI;
    public void checkUserAuthority(String token) throws Exception {
        String token2 = FirebaseAPIConfig.getBearerTokenHeader();
        token2 = token2.replace("Bearer","");

        if(token2 != null){
            try {
               //FirebaseReq req =  new FirebaseReq(token2);
                FirebaseRes user = firebaseAPI.tokenVerify(token2, "AIzaSyCjxgT9pk782PE1E50yp93OVeUb7aeGnmw");
            } catch (FeignException.FeignClientException | FeignException.FeignServerException ex) {
                System.out.println(ex.getMessage());
                throw new AuthorizationException(ex.getMessage());
            }


        } else {
            throw new AuthorizationException("Token is missing.");

        }

    }
}
