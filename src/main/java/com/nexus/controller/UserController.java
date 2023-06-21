package com.nexus.controller;

import com.nexus.model.Products;
import com.nexus.model.UserRole;
import com.nexus.model.UserType;
import com.nexus.model.Users;
import com.nexus.services.IUserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
@Validated

@SuppressWarnings("all")
public class UserController {

    @Autowired
    private IUserService userService;

    @PutMapping("/user")
    public ResponseEntity<Users> updateUser(@RequestBody Users newUser) {
        Users user = userService.updateUser(newUser);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user")
    public ResponseEntity<Users> newUser(@RequestBody Users newUser, WebRequest webRequest) throws Exception {
        Users user = userService.newUser(newUser);
        String uri = String.format("%s/company/%s", webRequest.getContextPath(), UUID.randomUUID());
        URI locationURI = new URI(uri);
        return ResponseEntity.created(locationURI).body(user);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Users>> listusers(@RequestParam(name = "userId", required = true) String userId, WebRequest webRequest) throws Exception {
        List<Users> userList = userService.findAllUsers(userId);
        if (!userList.isEmpty()) {
            return ResponseEntity.ok().body(userList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/userByRole/{userRole}")
    public ResponseEntity<List<Users>> userByRole(@PathVariable("userRole") UserRole userRole, @RequestParam(name = "userId", required = true) String userId, WebRequest webRequest) throws Exception {
        List<Users> userList = userService.findUserByRole(userRole.toString(), userId);
        if (!userList.isEmpty()) {
            return ResponseEntity.ok().body(userList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/userByType/{userType}")
    public ResponseEntity<List<Users>> userByType(@PathVariable("userType") UserType userType, @RequestParam(name = "userId", required = true) String userId, WebRequest webRequest) throws Exception {
        List<Users> userList = userService.findUserByType(userType.toString(), userId);
        if (!userList.isEmpty()) {
            return ResponseEntity.ok().body(userList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/userById/{userId}")
    public ResponseEntity<Users> userById(@PathVariable("userId") String userId, WebRequest webRequest) {
        Users user = userService.findUserById(userId);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/userByLocalId/{localId}")
    public ResponseEntity<Users> userByLocalId(@PathVariable("localId") String localId, WebRequest webRequest) {
        Users user = userService.findUserByLocalId(localId);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.noContent().build();
        }

    }
}
