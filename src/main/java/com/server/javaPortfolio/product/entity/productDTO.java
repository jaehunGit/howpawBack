package com.server.javaPortfolio.product.entity;


import lombok.*;

//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
@Data
public class productDTO {
    //PDC_NUMBER, PRODUCT_NAME, MAIN_IMAGE_ROUTE, BRAND_NAME, KIND, PRODUCT_TYPE

    private String pdcNumber;
    private String productName;
    private String mainImageRoute;
    private String brandName;
    private String kind;
    private String productType;
}
