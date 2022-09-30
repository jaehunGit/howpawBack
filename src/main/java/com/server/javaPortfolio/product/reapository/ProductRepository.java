package com.server.javaPortfolio.product.reapository;

import com.server.javaPortfolio.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "SELECT p.pdc_number, p.product_name, p.main_image_route, p.brand_name, p.kind, p.product_type FROM tb_product p", nativeQuery = true)
    List<Object[]> findByProductInfo();

    ProductEntity findByPdcNumber(String pdcNumber);

    List<ProductEntity> findByProductTypeContainsAndPdcNumberNot(String productType, String pdcNumber);


    ProductEntity findByProductType(String pdcNumber);

}