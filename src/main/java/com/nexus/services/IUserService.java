package com.nexus.services;

import com.nexus.model.Users;

import java.util.List;

public interface IUserService {


    Users newUser(Users user)   throws Exception;
    Users updateUser(Users user);
    List<Users> findAllUsers(String userId);
    Users findUserById(String id);
    List<Users> findUserByRole(String userRole, String userId);
    List<Users> findUserByType(String userType, String userId);

}
