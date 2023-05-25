package com.nexus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "products")
@Data
@Setter
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Products {

    public Products(){

    }
    @Id
    private String id;
    private String productName;
    private int productQuantity;
    private String productImgURL;
    private ProductType productType;
    private Metric productMetric;

    private Country productCountryOfOrigin;

    private String productDescription;

    private String userId;

}