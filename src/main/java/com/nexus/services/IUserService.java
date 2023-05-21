package com.nexus.services;

import com.nexus.model.Users;

import java.util.List;

public interface IUserService {


    Users newUser(Users user);
    Users updateUser(Users user);
    List<Users> findAllUsers();
    Users findUserById(String id);
    List<Users> findUserByRole(String userRole);
    List<Users> findUserByType(String userType);

}
