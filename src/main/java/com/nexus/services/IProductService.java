package com.nexus.services;

import com.nexus.model.Products;

import java.util.List;

public interface IProductService {

    Products saveProduct(Products product);
    Products updateProduct(Products product);

    List<Products> saveBulkProduct(List<Products> productList);
    List<Products> findAllProducts();
    List<Products> findProductsByCountry(String name);
    List<Products> findProductByType(String productType);

}
