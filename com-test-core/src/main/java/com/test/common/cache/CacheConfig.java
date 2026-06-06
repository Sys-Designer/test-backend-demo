
package com.test.common.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import com.sys.designer.framework.common.cache.redis.RedisDataCacheManager;
import org.springframework.context.annotation.Configuration;
import com.sys.designer.framework.api.cache.DataCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfig {
    @Bean
    @ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true")
    @ConditionalOnBean(RedisTemplate.class)
    public DataCacheManager masterCacheManager() {
        return new RedisDataCacheManager("master");
    }
}
