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
    public ResponseEntity<Users> updateUser(@RequestBody Users newUser){
        Users user = userService.updateUser(newUser);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user")
    public ResponseEntity<Users> newUser(@RequestBody Users newUser, WebRequest webRequest) throws Exception{
        Users user = userService.newUser(newUser);
        String uri = String.format("%s/company/%s", webRequest.getContextPath(), UUID.randomUUID());
        URI locationURI = new URI(uri);
        return ResponseEntity.created(locationURI).body(user);
    }

    @GetMapping("/user")
    public ResponseEntity< List<Users>> listusers(){
        List<Users> userList = userService.findAllUsers();
        if(!userList.isEmpty()){
            return ResponseEntity.ok().body(userList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/userByRole/{userRole}")
    public ResponseEntity< List<Users>> userByRole(@PathVariable("userRole") UserRole userRole){
        List<Users> userList = userService.findUserByRole(userRole.toString());
        if(!userList.isEmpty()){
            return ResponseEntity.ok().body(userList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/userByType/{userType}")
    public ResponseEntity< List<Users>> userByType(@PathVariable("userType") UserType userType){
        List<Users> userList = userService.findUserByType(userType.toString());
        if(!userList.isEmpty()){
            return ResponseEntity.ok().body(userList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/userById/{id}")
    public ResponseEntity<Users> userByType(@PathVariable("id") String id){
        Users user = userService.findUserById(id);
        if(user != null){
            return ResponseEntity.ok().body(user);
        }else{
            return ResponseEntity.noContent().build();
        }
    }




}
