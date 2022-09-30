package com.server.javaPortfolio.product.controller;

import com.server.javaPortfolio.product.entity.ProductEntity;
import com.server.javaPortfolio.product.reapository.ProductRepository;
import com.server.javaPortfolio.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("/api/SaveImage")
    public String[] saveImage(@RequestParam("mainImageFile") MultipartFile mainImageFile,
                              @RequestParam("detailImageFile") MultipartFile detailImageFile,
                              @RequestParam("bestReviewImageFile") MultipartFile bestReviewImageFile,
                              @RequestParam("worstReviewImageFile") MultipartFile worstReviewImageFile) throws IOException {
        String[] imageFilePath = new String[4];

        imageFilePath = productService.saveImageFile(mainImageFile, detailImageFile, bestReviewImageFile, worstReviewImageFile);

        return imageFilePath;
    }

    @PostMapping("/api/AddProduct")
    public ResponseEntity addProduct(@RequestBody ProductEntity productEntity) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProductInfo(productEntity));
    }

    @PostMapping("/api/GetProduct")
    public List<Object[]> getProduct() {
        return productService.getProductService();
    }

    @GetMapping("/api/GetProductInfo")
    public ResponseEntity getProductInfo(@RequestParam String pdcNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductInfoService(pdcNumber));
    }

    @GetMapping("/api/GetSimilarProduct")
    public ResponseEntity getSimilarProduct(@RequestParam String productType, @RequestParam String pdcNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.getSimilarProductService(productType, pdcNumber));
    }
}
