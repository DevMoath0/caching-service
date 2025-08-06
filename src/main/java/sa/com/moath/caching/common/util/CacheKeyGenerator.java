package sa.com.moath.caching.common.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class CacheKeyGenerator{

    public static String generateKey(Object... params) {
        String joined = Arrays.stream(params)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining("_"));
        return "Users_" + joined;
    }
}
