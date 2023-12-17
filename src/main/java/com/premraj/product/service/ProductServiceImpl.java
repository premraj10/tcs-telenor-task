package com.premraj.product.service;

import com.premraj.product.dto.ProductSearch;
import com.premraj.product.entity.Product;
import com.premraj.product.repository.ProductRepository;
import com.premraj.product.utility.CSVFileUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("productServiceImpl")
public class ProductServiceImpl implements  ProductService{

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CSVFileUtility csvFileUtility;

    @SneakyThrows
    public void save(MultipartFile file) {
        List<Product> products = csvFileUtility.csvToProducts(file);
        log.info("products saving {}", products);
        repository.saveAll(products);
    }

    public List<Product> searchProducts(ProductSearch productSearch) {
        log.debug("searchProducts");
        return repository.findAll().stream()
                .filter(product -> product.getProductType()
                        .contains(productSearch.getProductType()) &&
                        (product.getPrice() < productSearch.getMax() && product.getPrice() > productSearch.getMin())
                        && product.getStoreAddress().contains(productSearch.getCity()))
                .collect(Collectors.toList());
    }

}

