package com.premraj.product.controller;

import com.premraj.product.dto.Message;
import com.premraj.product.dto.ProductSearch;
import com.premraj.product.entity.Product;
import com.premraj.product.entity.Subscription;
import com.premraj.product.exception.EmptyProductException;
import com.premraj.product.service.ProductService;
import com.premraj.product.utility.CSVFileUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class ProductController {

    /*
     * First implementation using one Entity i.e. Product with productServiceImpl Qualifier
     * Second implementation using two Entities i.e. Phone and Subscription with phoneAndSubscriptionServiceImpl Qualifier
     * */

    @Autowired
    @Qualifier("productServiceImpl")
//    @Qualifier("phoneAndSubscriptionServiceImpl")
    private ProductService productService;

    @Autowired
    private CSVFileUtility csvFileUtility;

    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity<Message> uploadFile(@RequestParam("file") MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        MDC.put("trace-id", uuid.toString());
        log.debug("upload method call");
        csvFileUtility.validateFileFormat(file);
        productService.save(file);
        Message message = Message.builder()
                .statusCode(HttpStatus.OK.value())
                .description("Uploaded the " + file.getOriginalFilename() + " successfully ")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(message);

    }


    @GetMapping(value = "/product")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = Float.MIN_VALUE + "") Float min_price,
            @RequestParam(required = false, defaultValue = Float.MAX_VALUE + "") Float max_price,
            @RequestParam(required = false, defaultValue = "") String city) {
        UUID uuid = UUID.randomUUID();
        MDC.put("trace-id", uuid.toString());
        ProductSearch productSearch = ProductSearch.builder().productType(type).min(min_price).max(max_price).city(city).build();
        log.debug("searchProduct {}", productSearch);
        List<Product> products = productService.searchProducts(productSearch);
        if (products.isEmpty())
            throw new EmptyProductException("Products are not available");

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
