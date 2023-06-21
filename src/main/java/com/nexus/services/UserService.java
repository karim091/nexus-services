package com.nexus.services;

import com.nexus.api.FirebaseAPI;
import com.nexus.exception.NotFoundException;
import com.nexus.exception.UnHandledCustomException;
import com.nexus.model.Company;
import com.nexus.model.FirebaseSignupRequest;
import com.nexus.model.FirebaseSignupResponse;
import com.nexus.model.Users;
import com.nexus.repo.ICompanyRepo;
import com.nexus.repo.IUserRepo;
import com.nexus.utils.Helper;
import com.nexus.utils.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IUserRepo repo;

    @Autowired
    @Lazy
    private ICompanyServices companyServices;

    @Autowired
    private Notification notification;

    @Autowired
    @Lazy
    private FirebaseAPI firebaseAPI;

    @Override
    public Users newUser(Users user) throws Exception {

        try{
            // TODO FIREBASE SIGNUP
            FirebaseSignupResponse response = firebaseAPI.signUp(new FirebaseSignupRequest(user.getUserEmail(),false, user.getFullName(), true, user.getPassword()), "AIzaSyCjxgT9pk782PE1E50yp93OVeUb7aeGnmw");
            user.setLocalId(response.getLocalId());
            user.setIdToken(response.getIdToken());
            if (!user.getCompanyProfile().isEmpty()) {
                for (Company company : user.getCompanyProfile()) {
                    companyServices.newCompany(company);
                }
                Users userCreated = repo.insert(user);
                user.getCompanyProfile().forEach(c -> {
                    c.setUserId(userCreated.getId());
                    companyServices.updateCompany(c);
                });
                notification.sendEmail("New User registered ","New user been added to the system");
                return userCreated;
            } else {
                return repo.insert(user);
            }
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public Users updateUser(Users user) {
        if (findUserById(user.getId()) != null) {
            return repo.save(user);
        } else {
            throw new NotFoundException("User Id " + user.getId() + " is not exist.");
        }
    }

    @Override
    public List<Users> findAllUsers(String userId) throws Exception {
        return repo.findAll();
    }

    @Override
    public Users findUserById(String id) {
        Optional<Users> optUser = repo.findById(id);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new NotFoundException("User Id " + id + " is not exist.");
        }
    }

    @Override
    public List<Users> findUserByRole(String userRole, String userId) throws Exception {
        List<Users> optUser = repo.findUserByRole(userRole);
        if (!optUser.isEmpty()) {
            return optUser;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Users> findUserByType(String userType, String userId) throws Exception {
        List<Users> optUser = repo.findUserByType(userType);
        if (!optUser.isEmpty()) {
            return optUser;
        } else {
            return new ArrayList<>();
        }
    }
}
