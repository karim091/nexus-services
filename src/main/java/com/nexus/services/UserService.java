package com.nexus.services;

import com.nexus.exception.NotFoundException;
import com.nexus.exception.UnHandledCustomException;
import com.nexus.model.Company;
import com.nexus.model.Users;
import com.nexus.repo.ICompanyRepo;
import com.nexus.repo.IUserRepo;
import com.nexus.utils.Helper;
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
    private Helper helper;


    @Override
    public Users newUser(Users user) throws Exception {
        if (!user.getCompanyProfile().isEmpty()) {
            for (Company company : user.getCompanyProfile()) {
                companyServices.newCompany(company);
            }
            Users userCreated = repo.insert(user);
            user.getCompanyProfile().forEach(c -> {
                c.setUserId(userCreated.getId());
                companyServices.updateCompany(c);
            });
            return userCreated;
        } else {
            return repo.insert(user);
        }
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
        helper.checkUserAuthority(userId);
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
        helper.checkUserAuthority(userId);

        List<Users> optUser = repo.findUserByRole(userRole);
        if (!optUser.isEmpty()) {
            return optUser;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Users> findUserByType(String userType, String userId) throws Exception {
        helper.checkUserAuthority(userId);

        List<Users> optUser = repo.findUserByType(userType);
        if (!optUser.isEmpty()) {
            return optUser;
        } else {
            return new ArrayList<>();
        }
    }
}
