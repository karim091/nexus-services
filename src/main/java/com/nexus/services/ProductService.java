package com.nexus.services;

import com.nexus.exception.UnHandledCustomException;
import com.nexus.model.Products;
import com.nexus.model.ProductsDTO;
import com.nexus.model.Users;
import com.nexus.repo.IProductRepo;
import com.nexus.utils.Helper;
import com.nexus.utils.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Autowired
    @Lazy
    private IUserService userService;

    @Autowired
    private Helper helper;
    @Autowired
    private Notification notification;

    @Override
    public Products saveProduct(Products product, String userId) {
        try {
            Products finalProduct;
            Users user = userService.findUserById(userId);
            Products p = findProductByNameAndUserId(product.getProductName(), userId);
            if (p != null && p.getProductName().equalsIgnoreCase(product.getProductName())) {
                p.setProductQuantity(sumQuantity(p.getProductQuantity(), product.getProductQuantity()));
                finalProduct = updateProduct(p, userId);
            } else {
                product.setUserId(userId);
                finalProduct = repo.insert(product);
                user.getProductList().add(finalProduct);
                userService.updateUser(user);
            }
            notification.sendEmail("New Product","User " + user.getFullName() + " added A new product, Description: " + product.getProductDescription());

            return finalProduct;
        } catch (Exception ex) {
            throw new UnHandledCustomException("Data enrichment Issue.");
        }

    }

    private int sumQuantity(int oldQu, int newQu) {
        return oldQu + newQu;
    }

    @Override
    public Products updateProduct(Products product, String userId) {
        return repo.save(product);
    }

    @Override
    public Products findProductByNameAndUserId(String productName, String userId) {
        return repo.findProductByNameAndUserId(productName, userId);
    }

    @Override
    public List<Products> saveBulkProduct(List<Products> productList, String userId) {
        List<Products> productCreatedList = new ArrayList<>();
        Users user = userService.findUserById(userId);

        productList.forEach(product -> {
            Products p = saveProduct(product, userId);
            productCreatedList.add(p);
        });
        notification.sendEmail("New Bulk Product","User " + user.getFullName() + " added A list of product");

        return productCreatedList;

    }

    @Override
    public List<Products> findAllProducts(String userId) throws Exception {
        return repo.findAll();
    }


    @Override
    public List<Products> findProductsByCountry(String productCountryOfOrigin) {
        List<Products> optProduct = repo.findProductByCountry(productCountryOfOrigin);
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


    @Override
    public List<Products> findAllProductsByUserId(String adminUserId) {
        userService.findUserById(adminUserId);
        return repo.findProductByUserId(adminUserId);
    }

    @Override
    public List<ProductsDTO> findAllProductsAggregated(String userId) throws Exception {
        List<Products> productList = findAllProducts(userId);
        List<ProductsDTO> aggregatedList = new ArrayList<>();
        Map<String, Long> groupsNew = productList.stream().collect(Collectors.groupingBy(Products::getProductName, Collectors.summingLong(Products::getProductQuantity)));
        groupsNew.forEach((productName, quantity) -> {
            ProductsDTO dto = new ProductsDTO(productName, Integer.valueOf(quantity.toString()));
            aggregatedList.add(dto);
        });

        return aggregatedList;
    }

}
