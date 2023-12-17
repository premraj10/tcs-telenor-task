package com.premraj.product.service;

import com.premraj.product.dto.ProductSearch;
import com.premraj.product.entity.Phone;
import com.premraj.product.entity.Product;
import com.premraj.product.entity.Subscription;
import com.premraj.product.repository.PhoneRepository;
import com.premraj.product.repository.SubscriptionRepository;
import com.premraj.product.utility.CSVFileUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("phoneAndSubscriptionServiceImpl")
public class PhoneAndSubscriptionServiceImpl implements  ProductService{

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CSVFileUtility csvFileUtility;

    @SneakyThrows
    public void save(MultipartFile file) {
        List<Phone> phones = csvFileUtility.csvToPhones(file);
        log.info("phones saving {}", phones);
        phoneRepository.saveAll(phones);

        List<Subscription> subscriptions = csvFileUtility.csvToSubscriptions(file);
        log.info("subscriptions saving {}", subscriptions);
        subscriptionRepository.saveAll(subscriptions);
        log.info("data saved successfully");
    }

    public List<Product> searchProducts(ProductSearch productSearch) {
        log.debug("searchProducts");

        List<Product> products = new ArrayList<>();

        if(productSearch.getProductType().equals("phone")|| productSearch.getProductType().equals("")){
            List<Product> phones = phoneRepository.findAll().stream()
                    .filter(phone -> (phone.getPrice() < productSearch.getMax() && phone.getPrice() > productSearch.getMin())
                            && phone.getStoreAddress().contains(productSearch.getCity()))
                    .map(phone -> new Product(phone.getId(),"phone",phone.getColor(),0,phone.getPrice(),phone.getStoreAddress()))
                    .collect(Collectors.toList());
            products.addAll(phones);
        }
        if(productSearch.getProductType().equals("subscription") || productSearch.getProductType().equals("")){
            List<Product> subscriptions = subscriptionRepository.findAll().stream()
                    .filter(subscription -> (subscription.getPrice() < productSearch.getMax() && subscription.getPrice() > productSearch.getMin())
                            && subscription.getStoreAddress().contains(productSearch.getCity()))
                    .map(subscription -> new Product(subscription.getId(),"subscription","",subscription.getGbLimit(),subscription.getPrice(),subscription.getStoreAddress()))
                    .collect(Collectors.toList());
            products.addAll(subscriptions);

        }

        return products;

    }



}

