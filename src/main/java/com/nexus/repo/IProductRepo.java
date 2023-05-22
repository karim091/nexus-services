package com.nexus.repo;

import com.nexus.model.Products;
import com.nexus.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IProductRepo extends MongoRepository<Products, String> {

    @Query("{countryOfOrigin:'?0'}")
    List<Products> findProductByCountry(String countryOfOrigin);

    @Query("{productType:'?0'}")
    List<Products> findProductByType(String productType);

    @Query("{name:'?0'}")
    Products findProductByName(String name);

}
