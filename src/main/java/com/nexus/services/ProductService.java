package com.nexus.services;

import com.nexus.exception.NotFoundException;
import com.nexus.exception.UnHandledCustomException;
import com.nexus.model.Company;
import com.nexus.model.Products;
import com.nexus.model.Users;
import com.nexus.model.UsersProduct;
import com.nexus.repo.IProductRepo;
import com.nexus.repo.IUserProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Autowired
    @Lazy
    private IUserService userService;

    @Autowired
    private IUserProductsRepo userProductsRepo;

    @Override
    public Products saveProduct(Products product, String userId) {
        try {
            Products finalProduct;
            Users user = userService.findUserById(userId);
            if (user != null) {
                Products p = findProductByName(product.getName());
                if (p != null && p.getName().equalsIgnoreCase(product.getName())) {
                    p.setQuantity(sumQuantity(p.getQuantity(), product.getQuantity()));
                    finalProduct = updateProduct(p, userId);
                } else {
                    product.setUserId(userId);
                    finalProduct = repo.insert(product);
                    user.getProductList().add(finalProduct);
                    userService.updateUser(user);
                }

            } else {
                throw new NotFoundException("User Id is not exist.");
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
    public List<Products> saveBulkProduct(List<Products> productList, String userId) {
        List<Products> productCreatedList = new ArrayList<>();

        productList.forEach(product -> {
            Products p = saveProduct(product, userId);
            productCreatedList.add(p);
        });

        return null;

    }

    @Override
    public List<Products> findAllProducts() {
        return repo.findAll();
    }

    @Override
    public List<Products> findAllProductsAggregated() {
        List<Products> list = findAllProducts();
        List<Products> aggregatedList = new ArrayList<>();
        if (!list.isEmpty()) {
            // TODO Aggregate Products Quantities

            return aggregatedList;
        } else {
            return new ArrayList<>();
        }
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

    @Override
    public UsersProduct addProductsToUsers(Products products, String userId) {
        userService.findUserById(userId);
        List<Products> newProducts = new ArrayList<>();
        UsersProduct userProducts = userProductsRepo.findByUserId(userId);
        if (userProducts != null) {
            userProducts.getProductsList().forEach(oldProduct -> {
                if (oldProduct.getName().equalsIgnoreCase(products.getName())) {
                    oldProduct.setQuantity(sumQuantity(oldProduct.getQuantity(), products.getQuantity()));
                } else {
                    newProducts.add(products);
                }
            });
            if (!newProducts.isEmpty()) {
                userProducts.getProductsList().addAll(newProducts);
            }
            userProductsRepo.save(userProducts);
        } else {
            newProducts.add(products);
            userProducts = new UsersProduct();
            userProducts.setUserId(userId);
            userProducts.setProductsList(newProducts);
            userProductsRepo.insert(userProducts);
        }
        return userProducts;
    }

    @Override
    public List<UsersProduct> findAllUsersAndProducts() {
        return userProductsRepo.findAll();
    }

    @Override
    public List<Products> findAllUsersProducts() {
        List<Products> products = new ArrayList<>();
        List<UsersProduct> allUserProductsList = findAllUsersAndProducts();

        if (!allUserProductsList.isEmpty()) {
            allUserProductsList.forEach(obj -> products.addAll(obj.getProductsList()));
            return products;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Products> findProductsByUserId(String userId) {
        List<UsersProduct> allUserProductsList = findAllUsersAndProducts();
        List<Products> products = new ArrayList<>();
        ;
        if (!allUserProductsList.isEmpty()) {
            allUserProductsList.forEach(obj -> {
                if (obj.getUserId().equals(userId)) {
                    products.addAll(obj.getProductsList());
                }
            });
            return products;
        } else {
            return new ArrayList<>();
        }
    }
}
