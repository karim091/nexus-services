package com.nexus.services;

import com.nexus.model.Company;
import com.nexus.model.Users;

import java.util.List;

public interface ICompanyServices {


    Company newCompany(Company company)throws Exception ;
    List<Company> findAllCompanies(String userId);
    List<Company> findCompanyByCountry(String companyLocation);
    Company findCompanyByName(String companyName);

    Company updateCompany(Company company);
}
