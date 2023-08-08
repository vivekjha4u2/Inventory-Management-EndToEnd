package com.yunt.Product.service;

import com.yunt.Product.entity.Product;
import com.yunt.Product.exception.ProductServiceCustomException;
import com.yunt.Product.model.ProductRequest;
import com.yunt.Product.model.ProductResponse;
import com.yunt.Product.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("adding product...");
        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        productRepository.save(product);
        log.info("product created");
        log.info(product);
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("Getting product for id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Error occurred during getProductById");
                    return new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND");
                });
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    @Override
    public void reduceProductQuantity(long productId, long quantity) {
        log.info("Reducing quantity : {} for productId : {}",quantity,productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product with given id not found"
                        ,"PRODUCT_NOT_FOUND"));
        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient quantity"
                    ,"INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
    }

}
