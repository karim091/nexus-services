package com.nexus.repo;

import com.nexus.model.Company;
import com.nexus.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ICompanyRepo extends MongoRepository<Company, String> {

    @Query("{companyLocation:'?0'}")
    List<Company> findCompanyByLocation(String companyLocation);
    @Query("{companyName:'?0'}")

    Company findCompanyByName(String companyName);

}
