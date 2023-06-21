package com.nexus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FirebaseSignupResponse {

    private String idToken;
    private String displayName;
    private String email;
    private String localId;

}
