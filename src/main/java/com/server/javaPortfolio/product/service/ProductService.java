package com.server.javaPortfolio.product.service;

import com.server.javaPortfolio.product.entity.ProductEntity;
import com.server.javaPortfolio.product.entity.ResponseMessage;
import com.server.javaPortfolio.product.reapository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public String[] saveImageFile(MultipartFile mainImageFile,
                                  MultipartFile detailImageFile,
                                  MultipartFile bestReviewImageFile,
                                  MultipartFile worstReviewImageFile) throws IOException {

        String[] imageFilePath = new String[4];

        String mainImageProjectPath = "C:\\Users\\USER-PC\\OneDrive\\바탕 화면\\javaPortfolio\\client\\public\\Image\\Product\\Main";
        String detailImageProjectPath = "C:\\Users\\USER-PC\\OneDrive\\바탕 화면\\javaPortfolio\\client\\public\\Image\\Product\\Detail";
        String bestImageProjectPath = "C:\\Users\\USER-PC\\OneDrive\\바탕 화면\\javaPortfolio\\client\\public\\Image\\Product\\BestReview";
        String worstImageProjectPath = "C:\\Users\\USER-PC\\OneDrive\\바탕 화면\\javaPortfolio\\client\\public\\Image\\Product\\WorstReview";

        UUID uuid = UUID.randomUUID();

        String mainFileName = uuid + "_" + mainImageFile.getOriginalFilename();
        String detailFileName = uuid + "_" + detailImageFile.getOriginalFilename();
        String bestFileName = uuid + "_" + bestReviewImageFile.getOriginalFilename();
        String worstFileName = uuid + "_" + worstReviewImageFile.getOriginalFilename();

        File mainSaveFile = new File(mainImageProjectPath, mainFileName);
        File detailSaveFile = new File(detailImageProjectPath, detailFileName);
        File bestSaveFile = new File(bestImageProjectPath, bestFileName);
        File worstSaveFile = new File(worstImageProjectPath, worstFileName);

        mainImageFile.transferTo(mainSaveFile);
        detailImageFile.transferTo(detailSaveFile);
        bestReviewImageFile.transferTo(bestSaveFile);
        worstReviewImageFile.transferTo(worstSaveFile);

        imageFilePath[0] = mainImageProjectPath;
        imageFilePath[1] = detailImageProjectPath;
        imageFilePath[2] = bestImageProjectPath;
        imageFilePath[3] = worstImageProjectPath;

        return imageFilePath;
    }

    public ProductEntity addProductInfo(ProductEntity productEntity) {

        String count = String.format("%05d%n", (productRepository.count() + 1));
        String PDCNumber = "PDC-" + count;
        productEntity.setPdcNumber(PDCNumber);
        productRepository.save(productEntity);

        return productEntity;
    }

    public List<Object[]> getProductService() {

        return productRepository.findByProductInfo();
    }

    public ProductEntity getProductInfoService(String pdcNumber) {

        return productRepository.findByPdcNumber(pdcNumber);
    }


    public List<ProductEntity> getSimilarProductService(String productType, String pdcNumber) {

        productType = productType.split(",")[0];


        List<ProductEntity> result = productRepository.findByProductTypeContainsAndPdcNumberNot(productType, pdcNumber).stream()
                .sorted((s1, s2) -> Integer.compare(s1.getWorstRating() + s2.getBestRating(), s1.getBestRating() + s2.getWorstRating()))
                .limit(10)
                .collect(Collectors.toList());

        return result;
    }
}
