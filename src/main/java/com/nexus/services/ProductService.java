package com.nexus.services;

import com.nexus.exception.NotFoundException;
import com.nexus.model.Products;
import com.nexus.model.Users;
import com.nexus.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Override
    public Products saveProduct(Products product) {
        Products p = findProductByName(product.getName().eq);
        if(p != null && p.getName().equalsIgnoreCase(product.getName())) {
            int oldQu = p.getQuantity();
            int newQu = product.getQuantity();
            int totalQu = oldQu+newQu;
            p.setQuantity(totalQu);
            return updateProduct(p);
        }
        return repo.insert(product);
    }

    @Override
    public Products updateProduct(Products product) {
        if (repo.findById(product.getId()).isPresent()) {
            return repo.save(product);
        } else {
            throw new NotFoundException("Product: " + product.getId() + " is not exist.");
        }
    }

    @Override
    public Products findProductByName(String name) {
      return repo.findProductByName(name);
    }

    @Override
    public List<Products> saveBulkProduct(List<Products> productList) {
        List<Products> productCreatedList = new ArrayList<>();

        productList.forEach(product -> {
            Products p = saveProduct(product);
            productCreatedList.add(p);
        });

        return null;

    }

    @Override
    public List<Products> findAllProducts() {
        return repo.findAll();
    }

    @Override
    public List<Products> findProductsByCountry(String countryOfOrigin) {
        List<Products> optProduct = repo.findProductByCountry(countryOfOrigin);
        if (!optProduct.isEmpty()) {
            return optProduct;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Products> findProductByType(String productType) {
        List<Products> optProduct = repo.findProductByType(productType);
        if (!optProduct.isEmpty()) {
            return optProduct;
        } else {
            return new ArrayList<>();
        }
    }
}
