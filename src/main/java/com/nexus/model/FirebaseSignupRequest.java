package com.nexus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FirebaseSignupRequest {

    private String phoneNumber;

    private String email;
    private boolean emailVerified;
    private String displayName;
    private boolean returnSecureToken;

    private String password;


}
