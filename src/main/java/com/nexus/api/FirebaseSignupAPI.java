package com.nexus.api;


import com.nexus.model.FirebaseSignupRequest;
import com.nexus.model.FirebaseSignupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "firebaseSignup", url = "https://identitytoolkit.googleapis.com", configuration = FirebaseAPIConfig.class)
public interface FirebaseSignupAPI {
    @PostMapping("/v1/accounts:signUp")
    FirebaseSignupResponse signUp(@RequestBody FirebaseSignupRequest idToken, @RequestParam("key") String key);


}
