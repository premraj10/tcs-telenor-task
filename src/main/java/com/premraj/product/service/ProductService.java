package com.premraj.product.service;

import com.premraj.product.dto.ProductSearch;
import com.premraj.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void save(MultipartFile file);
    List<Product>  searchProducts(ProductSearch productSearch);
}
