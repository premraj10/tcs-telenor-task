package com.premraj.product.utility;

import com.premraj.product.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@ExtendWith(SpringExtension.class)
public class CSVFileUtilityTest {

    @InjectMocks
    private CSVFileUtility csvHelper;


    @Test
    void validateFileFormatSuccess() {
        MultipartFile file = new MockMultipartFile("file", "data.csv", "text/csv", "phone,color:grön,277.00,\"Blake gränden, Karlskrona\"".getBytes());
        assertDoesNotThrow(()->csvHelper.validateFileFormat(file));
    }

    @Test()
    void validateFileFormatServiceException() {
        MultipartFile file = new MockMultipartFile("files", "data.csv", "text/plain", "phone,color:grön,277.00,\"Blake gränden, Karlskrona\"".getBytes());
        assertThrows(ServiceException.class,()->csvHelper.validateFileFormat(file));
    }


    @Test()
    void csvToProducts() {
        MultipartFile file = new MockMultipartFile("files", "data.csv", "text/plain", "phone,color:grön,277.00,\"Blake gränden, Karlskrona\"".getBytes());
        assertDoesNotThrow(()->csvHelper.csvToProducts(file));
    }

    @Test()
    void csvToPhones() {
        MultipartFile file = new MockMultipartFile("files", "data.csv", "text/plain", "phone,color:grön,277.00,\"Blake gränden, Karlskrona\"".getBytes());
        assertDoesNotThrow(()->csvHelper.csvToPhones(file));
    }

    @Test()
    void csvToSubscription() {
        MultipartFile file = new MockMultipartFile("files", "data.csv", "text/plain", "phone,color:grön,277.00,\"Blake gränden, Karlskrona\"".getBytes());
        assertDoesNotThrow(()->csvHelper.csvToSubscriptions(file));
    }


}
