package sa.com.moath.caching.exception;

import lombok.Getter;

@Getter
public class CachingManagementException extends RuntimeException {

    private final String code;
    private Integer httpStatus;

    public CachingManagementException(String message, String code, Integer httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public CachingManagementException(String message, String code){
        super(message);
        this.code = code;
    }
}
