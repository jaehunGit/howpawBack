package com.server.javaPortfolio.product.entity;

import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;

@Entity(name = "tb_product")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) comment '상품번호'", nullable = false)
    private String pdcNumber;
    @Column(columnDefinition = "VARCHAR(50) comment '상품이름'", nullable = false)
    private String productName;
    @Column(columnDefinition = "INT(255) comment '상품가격'", nullable = false)
    private Integer productPrice;
    @Column(columnDefinition = "VARCHAR(50) comment '브랜드명'", nullable = false)
    private String brandName;
    @Column(columnDefinition = "VARCHAR(50) comment '원산지'", nullable = false)
    private String origin;
    @Column(columnDefinition = "VARCHAR(50) comment '상품무게'", nullable = false)
    private String productWeight;
    @Column(columnDefinition = "VARCHAR(255) comment '상품 링크'", nullable = false)
    private String productLink;
    @Column(columnDefinition = "VARCHAR(255) comment 'best 리뷰내용'", nullable = false)
    private String bestReviewText;
    @Column(columnDefinition = "VARCHAR(255) comment 'worst 리뷰내용'", nullable = false)
    private String worstReviewText;
    @Column(columnDefinition = "INT(255) comment 'best 평점'", nullable = false)
    private Integer bestRating;
    @Column(columnDefinition = "INT(255) comment 'worst 평점'", nullable = false)
    private Integer worstRating;
    @Column(columnDefinition = "VARCHAR(50) comment '동물 종류'", nullable = false)
    private String kind;
    @Column(columnDefinition = "VARCHAR(50) comment '상품 타입'", nullable = false)
    private String productType;
    @Column(columnDefinition = "VARCHAR(255) comment '대표이미지 경로'", nullable = false)
    private String mainImageRoute;
    @Column(columnDefinition = "VARCHAR(255) comment '상세이미지 경로'", nullable = false)
    private String detailImageRoute;
    @Column(columnDefinition = "VARCHAR(255) comment 'best 평점 이미지 경로'", nullable = false)
    private String bestImageRoute;
    @Column(columnDefinition = "VARCHAR(255) comment 'worst 평점 이미지 경로'", nullable = false)
    private String worstImageRoute;

    @Column(columnDefinition = "INT(255) comment 'Total 평점'", nullable = false)
    private int totalRating;
}


