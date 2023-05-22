package com.nexus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Setter
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users {

    public Users(){

    }
    @Id
    private String id;
    private String fullName;
    private String phoneNumber;

    private UserRole userRole;

    private UserType userType;

    private String userEmail;

    @DBRef
    private List<Company> companyProfile = new ArrayList<>();

    @DBRef
    private List<Products> productList = new ArrayList<>();

}
