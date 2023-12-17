package com.premraj.product.repository;

import com.premraj.product.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository  extends JpaRepository<Phone, Long> {
}
