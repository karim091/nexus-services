package com.nexus.controller;


import com.nexus.model.Company;
import com.nexus.model.Country;
import com.nexus.model.Products;
import com.nexus.model.Users;
import com.nexus.services.ICompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SuppressWarnings("all")
public class CompanyController {
    @Autowired
    private ICompanyServices companyServices;

    @PostMapping("/company")
    public ResponseEntity<Company> newCompany( @RequestBody Company company){
       Company createdCompany = companyServices.newCompany(company);
        if(company != null){
            return new ResponseEntity<>(HttpStatus.OK).ok(createdCompany);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> findAllCompanies(){
        List<Company> companyList = companyServices.findAllCompanies();
        if(!companyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(companyList);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/companiesByCountry/{country}")
    public ResponseEntity<List<Company>> findCompaniesByCountry(@PathVariable("country")Country country){
        List<Company> companyList = companyServices.findCompanyByCountry(country.toString());
        if(!companyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(companyList);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/companyByName/{countryName}")
    public ResponseEntity<Company> findCompaniesByName(@PathVariable("countryName")String  countryName){
        Company company = companyServices.findCompanyByName(countryName);
        if(company != null){
            return new ResponseEntity<>(HttpStatus.OK).ok(company);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
