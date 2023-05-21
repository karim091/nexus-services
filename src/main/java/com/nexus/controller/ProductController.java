package com.nexus.controller;

import com.nexus.model.Country;
import com.nexus.model.ProductType;
import com.nexus.model.Products;
import com.nexus.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
@SuppressWarnings("all")
public class ProductController {

    @Autowired
    private IProductService productService;


    @PostMapping("/products")
    public ResponseEntity<Products> newProduct( @RequestBody Products newProduct){
        Products product = productService.saveProduct(newProduct);
        if(product != null){
            return new ResponseEntity<>(HttpStatus.CREATED).ok(product);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bulkProducts")
    public ResponseEntity<List<Products>> bulkProducts( @RequestBody List<Products> productList){
        List<Products> products = productService.saveBulkProduct(productList);
        if(!products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CREATED).ok(products);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Products>> allProducts(){
        List<Products> productList = productService.findAllProducts();
        if(!productList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(productList);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/productsByType/{productType}")
    public ResponseEntity<List<Products>> getProductByType(@PathVariable("productType") ProductType productType){
        List<Products> productList = productService.findProductByType(productType.toString());
        if(!productList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(productList);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/productsByCountry/{country}")
    public ResponseEntity<List<Products>> getProductByType(@PathVariable("country") Country country){
        List<Products> productList = productService.findProductsByCountry(country.toString());
        if(!productList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK).ok(productList);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/products")
    public ResponseEntity<Products> updateProduct( @RequestBody Products product){
        Products updated = productService.updateProduct(product);
        if(updated != null){
            return new ResponseEntity<>(HttpStatus.OK).ok(updated);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
