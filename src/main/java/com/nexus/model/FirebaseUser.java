package com.nexus.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FirebaseUser {

    private String localId;
    private String email;
    private String emailVerified;
    private String displayName;

}
