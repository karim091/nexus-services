package com.nexus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class ProductsDTO {

    private String productName;
    private int productQuantities;
    private String productDescription;
    private Metric productMetric;

    private ProductType productType;

    private Set<String> usersIds;



}
