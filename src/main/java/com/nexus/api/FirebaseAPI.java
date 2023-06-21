package com.nexus.api;

import com.nexus.model.FirebaseReq;
import com.nexus.model.FirebaseRes;
import com.nexus.model.FirebaseSignupRequest;
import com.nexus.model.FirebaseSignupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "firebase", url = "https://www.googleapis.com", configuration = FirebaseAPIConfig.class)
public interface FirebaseAPI {

    @PostMapping("/identitytoolkit/v3/relyingparty/getAccountInfo")
    FirebaseRes tokenVerify(@RequestBody FirebaseReq idToken, @RequestParam("key") String key);

    @PostMapping("/v1/accounts:signUp")
    FirebaseSignupResponse signUp(@RequestBody FirebaseSignupRequest idToken, @RequestParam("key") String key);



}
