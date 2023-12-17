package com.premraj.product.utility;

import com.premraj.product.entity.Phone;
import com.premraj.product.entity.Product;
import com.premraj.product.entity.Subscription;
import com.premraj.product.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class CSVFileUtility {
    public static final String TYPE = "text/csv";

    public void validateFileFormat(MultipartFile file) {
        log.debug("validateFileFormat");
        Assert.notNull(file, "file must not be null");
        if (!TYPE.equals(file.getContentType())) {
            throw new ServiceException("Invalid file format, please upload only csv format file");
        }
        if (file.isEmpty()) {
            throw new ServiceException("Empty file");
        }
        log.info("validation is done successfully");
    }

    public List<Product> csvToProducts(MultipartFile file) {
        log.debug("csvToProducts");
        try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             BufferedReader fileReader = new BufferedReader(inputStreamReader);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {


            List<Product> products = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Object[] objects = Arrays.stream(csvRecord.get(1).split(":")).toArray();
                Product product = null;
                if (objects[0].toString().equals("color")) {
                    product = Product.builder().productType(csvRecord.get(0)).color(objects[1].toString()).price(Float.parseFloat(csvRecord.get(2))).storeAddress(csvRecord.get(3)).build();
                } else
                    product = Product.builder().productType(csvRecord.get(0)).color("").gbLimit(Integer.parseInt((String) objects[1])).price(Float.parseFloat(csvRecord.get(2))).storeAddress(csvRecord.get(3)).build();
                log.debug("product {}", product);
                products.add(product);
            }
            return products;
        } catch (IOException e) {
            throw new ServiceException("fail to parse the CSV file: " + e.getMessage());
        }
    }

    public List<Phone> csvToPhones(MultipartFile file) {
        log.debug("csvToPhones");
        try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             BufferedReader fileReader = new BufferedReader(inputStreamReader);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Phone> phones = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                if (csvRecord.get(0).toString().equals("phone")) {
                    Object[] objects = Arrays.stream(csvRecord.get(1).split(":")).toArray();
                    Phone phone = Phone.builder().color(objects[1].toString()).price(Float.parseFloat(csvRecord.get(2))).storeAddress(csvRecord.get(3)).build();
                    log.debug("phone {}", phone);
                    phones.add(phone);
                }
            }
            return phones;
        } catch (IOException e) {
            throw new ServiceException("fail to parse the CSV file: " + e.getMessage());
        }

    }

    public List<Subscription> csvToSubscriptions(MultipartFile file) {
        log.debug("csvToPhones");
        try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             BufferedReader fileReader = new BufferedReader(inputStreamReader);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Subscription> subscriptions = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                if (csvRecord.get(0).toString().equals("subscription")) {
                    Object[] objects = Arrays.stream(csvRecord.get(1).split(":")).toArray();
                    Subscription subscription = Subscription.builder().gbLimit(Integer.parseInt((String) objects[1])).price(Float.parseFloat(csvRecord.get(2))).storeAddress(csvRecord.get(3)).build();
                    log.debug("subscription {}", subscription);
                    subscriptions.add(subscription);
                }
            }
            return subscriptions;
        } catch (IOException e) {
            throw new ServiceException("fail to parse the CSV file: " + e.getMessage());
        }

    }
}
