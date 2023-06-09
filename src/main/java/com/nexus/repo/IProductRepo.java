package com.nexus.repo;

import com.nexus.model.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IProductRepo extends MongoRepository<Products, String> {

    @Query("{productCountryOfOrigin:'?0'}")
    List<Products> findProductByCountry(String productCountryOfOrigin);

    @Query("{productType:'?0'}")
    List<Products> findProductByType(String productType);

    @Query("{'productName': ?0, 'userId': ?1}")
    Products findProductByNameAndUserId(String productName, String userId);


    @Query("{'productName': ?0}")
    Products findProductByName(String productName);

    @Query("{userId:'?0'}")
    List<Products> findProductByUserId(String userId);


}
