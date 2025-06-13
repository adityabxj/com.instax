package instax.gateway_service.config;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        // Configure rate limiter: 100 requests per minute per user
        return new RedisRateLimiter(100, 200, 60);
    }
}
