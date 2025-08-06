package sa.com.moath.caching.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocaleResourceUtility {

    private static final String DEFAULT_MESSAGE = "Unknown";

    public static String getMessage(String key, String code) {
        if (key == null || key.isBlank()) {
            log.warn("Provided key is null or blank, returning default message");
            return DEFAULT_MESSAGE;
        }

        return key + " (" + code + ")";
    }

}
