package com.premraj.product.service;


import com.premraj.product.dto.ProductSearch;
import com.premraj.product.entity.Product;
import com.premraj.product.repository.ProductRepository;
import com.premraj.product.utility.CSVFileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private CSVFileUtility csvFileUtility;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void save() {
        List<Product> collect = Stream.of(new Product(1,"subscription","grön",10,100.00f,"Stockholm"),
                new Product(2,"subscription","pink",10,200.00f,"Stockholm")).collect(Collectors.toList());
        when(csvFileUtility.csvToProducts(any())).thenReturn(collect);
        assertDoesNotThrow(()-> productService.save(any()));
    }

    @Test
    void searchProductsSuccess() {
        ProductSearch productSearch = ProductSearch.builder().productType("subscription").max(1000).min(10).city("Stockholm").build();
        List<Product> collect = Stream.of(new Product(1,"subscription","grön",10,100.00f,"Stockholm"),
                new Product(2,"phone","pink",10,200.00f,"Stockholm")).collect(Collectors.toList());
        when(repository.findAll()).thenReturn(collect);
        List<Product> products = productService.searchProducts(productSearch);
        assertThat(products.size(), is(1));
    }

    @Test
    void searchProductsWithEmpty() {
        ProductSearch productSearch = ProductSearch.builder().productType("subscription").max(10).min(10).city("Stockholm").build();
        List<Product> collect = Stream.of(new Product(1,"subscription","grön",10,100.00f,"Stockholm"),
                new Product(2,"phone","pink",10,200.00f,"Stockholm")).collect(Collectors.toList());
        when(repository.findAll()).thenReturn(collect);
        List<Product> products = productService.searchProducts(productSearch);
        assertThat(products.size(), is(0));
    }
}