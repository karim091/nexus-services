package com.nexus.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FirebaseRes {


    private String kind;
    private List<FirebaseUser> users;

}
