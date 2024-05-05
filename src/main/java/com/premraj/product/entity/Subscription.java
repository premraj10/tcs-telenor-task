package com.premraj.product.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @JsonProperty("gb_limit")
    private int gbLimit;
    private float price;
    private String storeAddress;
}
