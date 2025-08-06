package sa.com.moath.caching.exception;

import lombok.Getter;

@Getter
public class CachingManagementBusinessException extends RuntimeException {

    private final String code;
    private Integer httpStatus;

    public CachingManagementBusinessException(String message, String code, Integer httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public CachingManagementBusinessException(String message, String code){
        super(message);
        this.code = code;
    }
}
