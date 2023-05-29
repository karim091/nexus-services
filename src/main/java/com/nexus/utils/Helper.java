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


   /* @Autowired
    @Lazy
    private FirebaseAPI firebaseAPI;
*/
   /* public void checkUserAuthority() throws Exception {
        String token = FirebaseAPIConfig.getBearerTokenHeader();
        token = token.replace("Bearer ", "");
//        token2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjJkM2E0YTllYjY0OTk0YzUxM2YyYzhlMGMwMTY1MzEzN2U5NTg3Y2EiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbmV4dXMtc2VydmljZXMtZjlmODciLCJhdWQiOiJuZXh1cy1zZXJ2aWNlcy1mOWY4NyIsImF1dGhfdGltZSI6MTY4NTM2ODMzMiwidXNlcl9pZCI6IkxHcW53Zlh3VEtSaW1PQ1VTMmtncnJaRkRpaDIiLCJzdWIiOiJMR3Fud2ZYd1RLUmltT0NVUzJrZ3JyWkZEaWgyIiwiaWF0IjoxNjg1MzY4MzMyLCJleHAiOjE2ODUzNzE5MzIsImVtYWlsIjoia2FyaW0uc2F5ZWQwOTFAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbImthcmltLnNheWVkMDkxQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.wXKiRLZEN5JTafD79hkvxqL1CcA1Nxftut3RJvJ65APYAlGRQbgs__tg8dlIKSTZ3_-d595bUv8L0DJagpqH7Xqw8E0MkKfMhPPz4HSg2ghrHmv4JV3BU0WTo4ONKkIitH1T0okmOzY81L8ETSRf3E9r-Q5aJ3nmTW7WEx_7ZsEYvaXfPHmL-5KlNU2SK-OO1ELT1HVAX38IgfgvG8e2mpMIPdQXsP8oXtrd4mhuF58268yTvdInRdIEXmpI9hAREUME0FUNj0-E30QmCFrz6C3tW8bhiVI1KFZs-wzWkyUPcvAvZzZTs_5pIKU-1TNw1F-YvUMsvoNGhEvcIhOzXg";

        if (token != null) {
            try {
                FirebaseReq req = new FirebaseReq(token);
                FirebaseRes user = firebaseAPI.tokenVerify(req, "AIzaSyCjxgT9pk782PE1E50yp93OVeUb7aeGnmw");
            } catch (FeignException.FeignClientException | FeignException.FeignServerException ex) {
                System.out.println(ex.getMessage());
                throw new AuthorizationException(ex.getMessage());
            }
        } else {
            throw new AuthorizationException("Token is missing.");

        }

    }*/
}
