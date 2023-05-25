package com.nexus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsDTO {
    public ProductsDTO(String productName, int productQuantities) {
        this.productName = productName;
        this.productQuantities = productQuantities;
        this.productMetric = Metric.KG;
    }
    public ProductsDTO(){

    }

    private String productName;
    private int productQuantities;
    private String productDescription;
    private Metric productMetric;

    private ProductType productType;

    private Set<String> usersIds;



}
