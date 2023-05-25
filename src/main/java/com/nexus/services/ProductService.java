package com.nexus.services;

import com.nexus.exception.NotFoundException;
import com.nexus.exception.UnHandledCustomException;
import com.nexus.model.Products;
import com.nexus.model.ProductsDTO;
import com.nexus.model.Users;
import com.nexus.repo.IProductRepo;
import com.nexus.utils.Helper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        return repo.findProductByName(productName, userId);
    }

    @Override
    public List<Products> saveBulkProduct(List<Products> productList, String userId) {
        List<Products> productCreatedList = new ArrayList<>();

        productList.forEach(product -> {
            Products p = saveProduct(product, userId);
            productCreatedList.add(p);
        });

        return null;

    }

    @Override
    public List<Products> findAllProducts(String userId) {
        helper.checkUserAuthority(userId);
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
    public List<ProductsDTO> findAllProductsAggregated(String userId) {
        Iterator<Products> it = findAllProducts(userId).iterator();
        List<ProductsDTO> aggregatedList = new ArrayList<>();
        // TO avoid ConcurrentModificationException
        List<ProductsDTO> toBeAdded = new ArrayList<>();
        Set<String> userIds = new HashSet<>();

        while (it.hasNext()) {
            Products p1 = it.next();
            if (aggregatedList.isEmpty()) {
                userIds.add(p1.getUserId());
                ProductsDTO dto = new ProductsDTO(p1.getProductName(), p1.getProductQuantity(), p1.getProductDescription(), p1.getProductMetric(), p1.getProductType(), userIds);
                aggregatedList.add(dto);
            } else {
                for (ProductsDTO agg : aggregatedList) {
                    if (p1.getProductName().equalsIgnoreCase(agg.getProductName())) {
                        agg.setProductQuantities(sumQuantity(p1.getProductQuantity(), agg.getProductQuantities()));
                    } else {
                        userIds.add(p1.getUserId());
                        ProductsDTO dto = new ProductsDTO(p1.getProductName(), p1.getProductQuantity(), p1.getProductDescription(), p1.getProductMetric(), p1.getProductType(), userIds);
                        toBeAdded.add(dto);
                    }
                }
            }
        }
        aggregatedList.addAll(toBeAdded);
        return aggregatedList;
    }

}
