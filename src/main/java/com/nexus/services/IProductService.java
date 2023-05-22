package com.nexus.services;

import com.nexus.model.Products;
import com.nexus.model.UsersProduct;

import java.util.List;
import java.util.Map;

public interface IProductService {

    Products saveProduct(Products product, String userId);
    Products updateProduct(Products product, String userId);

    Products findProductByName(String name);

    List<Products> saveBulkProduct(List<Products> productList, String userId);
    List<Products> findAllProducts();
    List<Products> findAllProductsAggregated();


    List<Products> findProductsByCountry(String name);
    List<Products> findProductByType(String productType);
    UsersProduct addProductsToUsers(Products products, String userId);
    List<UsersProduct> findAllUsersAndProducts();
    List<Products>  findAllUsersProducts();
    List<Products> findProductsByUserId(String userId);

}
