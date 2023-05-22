package com.nexus.controller;

import com.nexus.model.Country;
import com.nexus.model.ProductType;
import com.nexus.model.Products;
import com.nexus.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("api")
@Validated

@SuppressWarnings("all")
public class ProductController {

    @Autowired
    private IProductService productService;


    @PostMapping("/products")
    public ResponseEntity<Products> newProduct( @RequestBody Products newProduct, WebRequest webRequest) throws Exception {
        Products product = productService.saveProduct(newProduct);
        String uri = String.format("%s/products/%s", webRequest.getContextPath(), UUID.randomUUID());
        URI locationURI = new URI(uri);
        return ResponseEntity.created(locationURI).body(product);
    }

    @PostMapping("/bulkProducts")
    public ResponseEntity<List<Products>> bulkProducts( @RequestBody List<Products> productList, WebRequest webRequest) throws Exception {
        List<Products> products = productService.saveBulkProduct(productList);
        String uri = String.format("%s/products/%s", webRequest.getContextPath(), UUID.randomUUID());
        URI locationURI = new URI(uri);
        return ResponseEntity.created(locationURI).body(products);

    }

    @GetMapping("/products")
    public ResponseEntity<List<Products>> allProducts(){
        List<Products> productList = productService.findAllProducts();
        if(!productList.isEmpty()){
            return ResponseEntity.ok().body(productList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/productsByType/{productType}")
    public ResponseEntity<List<Products>> getProductByType(@PathVariable("productType") ProductType productType){
        List<Products> productList = productService.findProductByType(productType.toString());
        if(!productList.isEmpty()){
            return ResponseEntity.ok().body(productList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("/productsByCountry/{country}")
    public ResponseEntity<List<Products>> getProductByType(@PathVariable("country") Country country){
        List<Products> productList = productService.findProductsByCountry(country.toString());
        if(!productList.isEmpty()){
            return ResponseEntity.ok().body(productList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/products")
    public ResponseEntity<Products> updateProduct( @RequestBody Products product){
        Products updated = productService.updateProduct(product);
        return ResponseEntity.ok().body(updated);
    }


}
