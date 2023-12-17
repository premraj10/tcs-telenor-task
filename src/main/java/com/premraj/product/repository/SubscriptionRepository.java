package com.premraj.product.repository;

import com.premraj.product.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository  extends JpaRepository<Subscription, Long> {
}

