package com.nexus.api;

import com.nexus.model.FirebaseReq;
import com.nexus.model.FirebaseRes;
import com.nexus.model.FirebaseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "firebase", url = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/getAccountInfo", configuration = FirebaseAPIConfig.class)
public interface FirebaseAPI {

    @PostMapping
    FirebaseRes tokenVerify(@RequestBody FirebaseReq idToken, @RequestParam("key") String key);
}
