package sa.com.moath.caching.configuration;

import lombok.Getter;

@Getter
public enum Header {

    USER_ID("user-id");

    Header(String key) {
        this.key = key;
    }

    private final String key;

}
