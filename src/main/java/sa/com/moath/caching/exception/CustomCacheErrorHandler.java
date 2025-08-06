package sa.com.moath.caching.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error("could not get the cache for key '{}'", key);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.error("could not put the cache for key '{}'", key);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error("could not delete the cache for key '{}'", key);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error("could not clear the cache");
    }

}