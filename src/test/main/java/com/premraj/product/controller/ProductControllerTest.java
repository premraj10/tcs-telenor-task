package com.premraj.product.controller;

import com.premraj.product.dto.ProductSearch;
import com.premraj.product.entity.Product;
import com.premraj.product.exception.ControllerExceptionHandler;
import com.premraj.product.exception.EmptyProductException;
import com.premraj.product.service.ProductServiceImpl;
import com.premraj.product.utility.CSVFileUtility;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductServiceImpl productServiceImpl;

    @Mock
    private CSVFileUtility csvFileUtility;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .build();
    }

    @Test
    public void testFileUpoloadIsOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "data.csv", "multipart/form-data", "phone,color:grön,277.00,\"Blake gränden, Karlskrona".getBytes());
        mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testFileUpoloadIs4xxClientError() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "data.csv", "multipart/form-data", "phone,color:grön,277.00,\"Blake gränden, Karlskrona".getBytes());
        mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().is4xxClientError());
    }


    @SneakyThrows
    @Test
    public void searchProducts(){
        ProductSearch productSearch = ProductSearch.builder().productType("subscription").max(1000).min(900).city("Stockholm").build();
        List<Product> collect = Stream.of(new Product(1,"subscription","grön",10,100.00f,"Stockholm"),
                new Product(2,"subscription","pink",10,200.00f,"Stockholm")).collect(Collectors.toList());
        when(productServiceImpl.searchProducts(productSearch)).thenReturn(collect);
        mockMvc.perform(get("/product?type=subscription&min_price=900&max_price=1000&city=Stockholm"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].productType", Matchers.is("subscription")));

    }

    @SneakyThrows
    @Test
    public void searchProductsReturnEmpty(){
        mockMvc.perform(get("/product?type=subscription&max_price=1000&city=Stockholm"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyProductException))
                .andExpect(result -> assertEquals("Products are not available", result.getResolvedException().getMessage()));

    }





}

