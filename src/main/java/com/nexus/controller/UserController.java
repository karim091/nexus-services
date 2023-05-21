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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SuppressWarnings("all")
public class UserController {

    @Autowired
    private IUserService userService;

    @PutMapping("/user")
    public ResponseEntity<Users> updateUser(@RequestBody Users newUser){
        Users user = userService.updateUser(newUser);
        if(user != null){
            return new ResponseEntity<>(HttpStatus.CREATED).ok(user);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Users> newUser(@RequestBody Users newUser){
        Users user = userService.newUser(newUser);
        if(user != null){
            return new ResponseEntity<>(HttpStatus.CREATED).ok(user);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    public ResponseEntity< List<Users>> listusers(){
        List<Users> userList = userService.findAllUsers();
        if(!userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(userList);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/userByRole/{userRole}")
    public ResponseEntity< List<Users>> userByRole(@PathVariable("userRole") UserRole userRole){
        List<Users> userList = userService.findUserByRole(userRole.toString());
        if(!userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(userList);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userByType/{userType}")
    public ResponseEntity< List<Users>> userByType(@PathVariable("userType") UserType userType){
        List<Users> userList = userService.findUserByType(userType.toString());
        if(!userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(userList);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userById/{id}")
    public ResponseEntity<Users> userByType(@PathVariable("id") String id){
        Users user = userService.findUserById(id);
        if(user != null){
            return new ResponseEntity<>(HttpStatus.OK).ok(user);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
