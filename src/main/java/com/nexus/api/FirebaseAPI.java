package com.nexus.api;

import com.nexus.model.FirebaseReq;
import com.nexus.model.FirebaseRes;
import com.nexus.model.FirebaseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "firebase", url = "https://identitytoolkit.googleapis.com/v1/accounts:lookup/", configuration = FirebaseAPIConfig.class)
public interface FirebaseAPI {

    @PostMapping
    FirebaseRes tokenVerify(@RequestBody String idToken, @RequestParam("key") String key);
}
