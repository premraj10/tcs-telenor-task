package com.premraj.product.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;

@RestController
public class Resilience4jController {

    private static final String SERVICE_NAME = "myService";

    // 1️⃣ Circuit Breaker: If an external API fails, this prevents repeated calls
    @GetMapping("/circuit")
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "circuitBreakerFallback")
    public String circuitBreakerDemo(@RequestParam(required = false) Boolean fail) {
        if (Boolean.TRUE.equals(fail)) {
            throw new RuntimeException("Simulated Service Failure!");
        }
        return "✅ Successful Response at " + LocalTime.now();
    }

    public String circuitBreakerFallback(Boolean fail, Throwable t) {
        return "⚠️ Circuit Breaker Fallback: Service is temporarily unavailable!";
    }

    // 2️⃣ Rate Limiter: Allows only a limited number of calls per time window
    @GetMapping("/ratelimit")
    @RateLimiter(name = SERVICE_NAME)
    public String rateLimiterDemo() {
        return "✅ Rate Limit Check Passed at " + LocalTime.now();
    }

    // 3️⃣ Retry: Retries a failing API call before returning an error
    @GetMapping("/retry")
    @Retry(name = SERVICE_NAME, fallbackMethod = "retryFallback")
    public String retryDemo(@RequestParam(required = false) Boolean fail) {
        if (Boolean.TRUE.equals(fail)) {
            throw new RuntimeException("Simulated Failure! Retrying...");
        }
        return "✅ Successful Response after Retry at " + LocalTime.now();
    }

    public String retryFallback(Boolean fail, Throwable t) {
        return "⚠️ Retry Fallback: Service is still failing after retries!";
    }
}
