package com.nexus.controller;

import com.nexus.model.Country;
import com.nexus.model.ProductType;
import com.nexus.model.Products;
import com.nexus.model.ProductsDTO;
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
    public ResponseEntity<Products> newProduct(@RequestBody Products newProduct, WebRequest webRequest, @RequestParam(name = "userId", required = true) String userId) throws Exception {
        Products product = productService.saveProduct(newProduct, userId);
        String uri = String.format("%s/products/%s", webRequest.getContextPath(), UUID.randomUUID());
        URI locationURI = new URI(uri);
        return ResponseEntity.created(locationURI).body(product);
    }

    @PostMapping("/bulkProducts")
    public ResponseEntity<List<Products>> bulkProducts( @RequestBody List<Products> productList, WebRequest webRequest, @RequestParam(name = "userId", required = true) String userId) throws Exception {
        List<Products> products = productService.saveBulkProduct(productList, userId);
        String uri = String.format("%s/products/%s", webRequest.getContextPath(), UUID.randomUUID());
        URI locationURI = new URI(uri);
        return ResponseEntity.created(locationURI).body(products);

    }

    // ADMIN ONLY
    @GetMapping("/productsAggregated")
    public ResponseEntity<List<ProductsDTO>> allProducts(@RequestParam(name = "userId", required = true) String userId, WebRequest webRequest) throws Exception {
        List<ProductsDTO> productList = productService.findAllProductsAggregated(userId);
        if(!productList.isEmpty()){
            return ResponseEntity.ok().body(productList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    // ADMIN
    @GetMapping("/productsByUserId")
    public ResponseEntity<List<Products>> allProductsByUserId(@RequestParam(name = "userId", required = true) String userId, WebRequest webRequest){
        List<Products> productList = productService.findAllProductsByUserId(userId);
        if(!productList.isEmpty()){
            return ResponseEntity.ok().body(productList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/productsByType/{productType}")
    public ResponseEntity<List<Products>> getProductByType(@PathVariable("productType") ProductType productType, @RequestParam(name = "userId", required = true) String userId, WebRequest webRequest){
        List<Products> productList = productService.findProductByType(productType.toString());
        if(!productList.isEmpty()){
            return ResponseEntity.ok().body(productList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("/productsByCountry/{country}")
    public ResponseEntity<List<Products>> getProductByType(@PathVariable("country") Country country, @RequestParam(name = "userId", required = true) String userId, WebRequest webRequest){
        List<Products> productList = productService.findProductsByCountry(country.toString());
        if(!productList.isEmpty()){
            return ResponseEntity.ok().body(productList);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/products")
    public ResponseEntity<Products> updateProduct(@RequestBody Products product, @RequestParam(name = "userId", required = true) String userId, WebRequest webRequest){
        Products updated = productService.updateProduct(product, userId);
        return ResponseEntity.ok().body(updated);
    }


}
