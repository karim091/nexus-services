package com.nexus.services;

import com.nexus.model.Products;
import com.nexus.model.ProductsDTO;

import java.util.List;

public interface IProductService {

    Products saveProduct(Products product, String userId);
    Products updateProduct(Products product, String userId);

    Products findProductByNameAndUserId(String productName, String userId);

    //List<Products> findAProductByName(String productName);


    List<Products> saveBulkProduct(List<Products> productList, String userId);
    List<Products> findAllProducts(String adminUserId) throws Exception ;

    List<Products> findProductsByCountry(String productCountryOfOrigin);
    List<Products> findProductByType(String productType);

    List<Products> findAllProductsByUserId(String userId);

    List<ProductsDTO> findAllProductsAggregated(String userId) throws Exception ;
}
