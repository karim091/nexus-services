package com.nexus.services;

import com.nexus.exception.MissingDataException;
import com.nexus.exception.NotFoundException;
import com.nexus.model.Company;
import com.nexus.model.Users;
import com.nexus.repo.ICompanyRepo;
import com.nexus.repo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyService implements ICompanyServices {

    @Autowired
    private ICompanyRepo repo;

    @Autowired
    private IUserService userService;


    @Override
    public Company newCompany(Company company) throws Exception {

            if (company.getUserId() != null) {
                Users user = userService.findUserById(company.getUserId());
                if (user == null) {
                    throw new NotFoundException("User Id is not exist.");
                } else {
                    Company createdCompany = repo.insert(company);
                    user.getCompanyProfile().add(createdCompany);
                    userService.updateUser(user);
                    return createdCompany;
                }
            }else {
                return repo.insert(company);
            }

    }

    @Override
    public Company updateCompany(Company company) {
        if (userService.findUserById(company.getUserId()) == null) {
            throw new NotFoundException("User Id " + company.getUserId() + " is not exist.");
        }
        if (repo.findById(company.getId()).isPresent()) {
            return repo.save(company);
        } else {
            throw new NotFoundException("Company Id " + company.getId() + " is not exist.");
        }
    }

    @Override
    public List<Company> findAllCompanies() {
        return repo.findAll();
    }

    @Override
    public List<Company> findCompanyByCountry(String companyLocation) {
        List<Company> optCompany = repo.findCompanyByLocation(companyLocation);
        if (!optCompany.isEmpty()) {
            return optCompany;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public Company findCompanyByName(String companyName) {
        Company optCompany = repo.findCompanyByName(companyName);
        if (optCompany != null) {
            return optCompany;
        } else {
            throw new NotFoundException("Company " + companyName + " is not exist.");
        }

    }


}
