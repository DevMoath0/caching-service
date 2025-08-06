package sa.com.moath.caching.common.constant;

import lombok.Getter;

@Getter
public enum CachingError {

    UNKNOWN_ERROR("BUSINESS", "unknown.error", "CAH001"),
    USER_NOT_FOUND("BUSINESS", "user.not.found", "CAH051"),
    USER_ID_REQUIRED("BUSINESS", "user.id.required", "CAH050");

    private final String type;
    private final String message;
    private final String code;

    CachingError(String type, String message, String code) {
        this.type = type;
        this.message = message;
        this.code = code;
    }
}
