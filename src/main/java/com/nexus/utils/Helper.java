package com.nexus.utils;

import com.nexus.exception.AuthorizationException;
import com.nexus.model.UserRole;
import com.nexus.model.Users;
import com.nexus.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Helper {


    @Autowired
    @Lazy
    private IUserService userService;
    public void checkUserAuthority(String userId) throws Exception {
        Users user = userService.findUserById(userId);
        if(!user.getUserRole().equals(UserRole.ADMIN)) {
            throw new AuthorizationException("User is not authorize for this action.");
        }
    }
}
