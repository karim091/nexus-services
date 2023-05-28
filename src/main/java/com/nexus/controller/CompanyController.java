package com.nexus.controller;


import com.nexus.model.Company;
import com.nexus.model.Country;
import com.nexus.model.Products;
import com.nexus.model.Users;
import com.nexus.services.ICompanyServices;
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
public class CompanyController {
    @Autowired
    private ICompanyServices companyServices;

    @PostMapping("/company")
    public ResponseEntity<Company> newCompany(@RequestBody Company company, WebRequest webRequest, @RequestParam(name = "userId", required = true) String userId) throws Exception {
        Company createdCompany = companyServices.newCompany(company);
        String uri = String.format("%s/company/%s", webRequest.getContextPath(), UUID.randomUUID());
        URI locationURI = new URI(uri);
        return ResponseEntity.created(locationURI).body(createdCompany);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> findAllCompanies(@RequestParam(name = "userId", required = true) String userId, WebRequest webRequest) throws Exception {
        List<Company> companyList = companyServices.findAllCompanies(userId);
        if (!companyList.isEmpty()) {
            return ResponseEntity.ok().body(companyList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/companiesByCountry/{country}")
    public ResponseEntity<List<Company>> findCompaniesByCountry(@PathVariable("country") Country country, @RequestParam(name = "userId", required = true) String userId, WebRequest webRequest) {
        List<Company> companyList = companyServices.findCompanyByCountry(country.toString());
        if (!companyList.isEmpty()) {
            return ResponseEntity.ok().body(companyList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/companyByName/{countryName}")
    public ResponseEntity<Company> findCompaniesByName(@PathVariable("countryName") String countryName, @RequestParam(name = "userId", required = true) String userId, WebRequest webRequest) {
        Company company = companyServices.findCompanyByName(countryName);
        return ResponseEntity.ok().body(company);
    }

}
