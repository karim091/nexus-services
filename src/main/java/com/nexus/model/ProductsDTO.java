package com.nexus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class ProductsDTO {
    public ProductsDTO(String productName, int productQuantities) {
        this.productName = productName;
        this.productQuantities = productQuantities;
    }
    public ProductsDTO(){

    }

    private String productName;
    private int productQuantities;
    private String productDescription;
    private Metric productMetric;

    private ProductType productType;

    @FieldNameConstants.Exclude
    private Set<String> usersIds = new HashSet<>();



}
