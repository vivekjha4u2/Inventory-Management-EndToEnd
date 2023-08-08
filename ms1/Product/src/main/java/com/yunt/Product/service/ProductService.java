package com.yunt.Product.service;

import com.yunt.Product.model.ProductRequest;
import com.yunt.Product.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long id);

    void reduceProductQuantity(long productId, long quantity);
}
