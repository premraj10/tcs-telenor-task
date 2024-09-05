package com.premraj.product;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class ProductApplicationTest {


//    @Test
    void main() {
        String[] args={};
        assertDoesNotThrow(()-> ProductApplication.main(args));
    }
}