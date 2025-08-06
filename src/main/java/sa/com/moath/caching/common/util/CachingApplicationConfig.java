package sa.com.moath.caching.common.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import sa.com.moath.caching.configuration.ServicesCache;

import java.util.Map;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cachingapp.config")
public class CachingApplicationConfig {

    private Map<String, ServicesCache> servicesCache;

}
