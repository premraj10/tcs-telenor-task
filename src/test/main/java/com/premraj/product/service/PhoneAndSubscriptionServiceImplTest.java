package com.premraj.product.service;

import com.premraj.product.dto.ProductSearch;
import com.premraj.product.entity.Phone;
import com.premraj.product.entity.Product;
import com.premraj.product.entity.Subscription;
import com.premraj.product.repository.PhoneRepository;
import com.premraj.product.repository.SubscriptionRepository;
import com.premraj.product.utility.CSVFileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneAndSubscriptionServiceImplTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CSVFileUtility csvFileUtility;

    @InjectMocks
    private PhoneAndSubscriptionServiceImpl productService;

    @Test
    void save() {
        List<Phone> phones = Stream.of(new Phone(1,"grön",100.00f,"Stockholm"),
                new Phone(2,"pink",200.00f,"Stockholm")).collect(Collectors.toList());
        List<Subscription> subscriptions = Stream.of(new Subscription(1,10,100.00f,"Stockholm"),
                new Subscription(2,10,200.00f,"Stockholm")).collect(Collectors.toList());
        when(csvFileUtility.csvToPhones(any())).thenReturn(phones);
        when(csvFileUtility.csvToSubscriptions(any())).thenReturn(subscriptions);
        assertDoesNotThrow(()-> productService.save(any()));
    }


    @Test
    void setProductServiceForPhonesOk() {
        ProductSearch productSearch = ProductSearch.builder().productType("subscription").max(1000).min(10).city("Stockholm").build();
        List<Subscription> subscriptions = Stream.of(new Subscription(1,10,100.00f,"Stockholm"),
                new Subscription(2,10,200.00f,"Stockholm")).collect(Collectors.toList());
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        List<Product> products = productService.searchProducts(productSearch);
        assertThat(products.size(), is(2));
    }

    @Test
    void setProductServiceForSubscriptionOk() {
        ProductSearch productSearch = ProductSearch.builder().productType("phone").max(1000).min(10).city("Stockholm").build();
        List<Phone> phones = Stream.of(new Phone(1,"grön",100.00f,"Stockholm"),
                new Phone(2,"pink",200.00f,"Stockholm")).collect(Collectors.toList());
        when(phoneRepository.findAll()).thenReturn(phones);
        List<Product> products = productService.searchProducts(productSearch);
        assertThat(products.size(), is(2));
    }


}