package sa.com.moath.caching.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sa.com.moath.caching.common.util.CachingApplicationConfig;
import sa.com.moath.caching.exception.CustomCacheErrorHandler;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisCacheConfig implements CachingConfigurer {

    private final CachingApplicationConfig config;

    @Value("${spring.data.redis.host}")
    private String hostName;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.username:#{null}}")
    private String userName;

    @Value("${spring.data.redis.password:#{null}}")
    private String password;

    @Value("${spring.data.redis.timeout}")
    private Duration timeout = Duration.ofMillis(1000);

    @Value("${spring.data.redis.ssl.enabled}")
    private boolean sslEnabled = false;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);
        if (userName != null && !userName.isBlank()) {
            redisStandaloneConfiguration.setUsername(userName);
        }
        if (password != null && !password.isBlank()) {
            redisStandaloneConfiguration.setPassword(password);
        }

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.readTimeout(timeout);
        if (sslEnabled) {
            jedisClientConfigurationBuilder.useSsl();
        }
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfigurationBuilder.build());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public RedisCacheWriter redisCacheWriter() {
        return RedisCacheWriter.lockingRedisCacheWriter(jedisConnectionFactory());
    }

    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig();
    }

    @Bean
    public CacheManager redisCacheManager() {
        Map<String, RedisCacheConfiguration> cacheNamesConfigurationMap = new HashMap<>();

        Map<String, ServicesCache> servicesCache = config.getServicesCache();
        if (servicesCache != null && !servicesCache.isEmpty()) {
            servicesCache.values().forEach(cacheValue -> {
                cacheNamesConfigurationMap.put(
                        cacheValue.getName(),
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(cacheValue.getExpireDuration())
                );
            });
        }

        RedisCacheManager redisCacheManager = new RedisCacheManager(
                redisCacheWriter(),
                defaultRedisCacheConfiguration(),
                cacheNamesConfigurationMap
        );
        redisCacheManager.setTransactionAware(false);
        return redisCacheManager;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }

}
