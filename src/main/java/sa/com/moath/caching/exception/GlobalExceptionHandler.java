package sa.com.moath.caching.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sa.com.moath.openapi.server.model.Error;
import sa.com.moath.openapi.server.model.ErrorResponse;
import sa.com.moath.caching.common.constant.CachingError;
import sa.com.moath.caching.common.util.LocaleResourceUtility;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String UNMAPPED_ERROR_KEY = "unknown.error";


    @ExceptionHandler
    public final ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Global exception handler with message '{}'", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse().error(new Error().code(CachingError.UNKNOWN_ERROR.getCode())
                        .message(LocaleResourceUtility.getMessage(exception.getMessage(), CachingError.UNKNOWN_ERROR.getCode()))
                        .key(validateExceptionKey(exception.getMessage()))),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public final ResponseEntity<ErrorResponse> handleCachingManagementBusinessException(CachingManagementBusinessException redisException) {
        log.error("Global exception handler with message '{}'", redisException.getMessage());
        return new ResponseEntity<>(new ErrorResponse().error(new Error().code(CachingError.UNKNOWN_ERROR.getCode())
                .message(LocaleResourceUtility.getMessage(redisException.getMessage(), CachingError.UNKNOWN_ERROR.getCode()))
                .key(validateExceptionKey(redisException.getMessage()))),
                HttpStatus.BAD_REQUEST
        );    }

    private String validateExceptionKey(String key) {
        if (key != null && !key.isBlank() && key.indexOf(' ') == -1) {
            return key;
        }
        log.warn("Global exception handler for unmapped to error key with message '{}'", key);
        return UNMAPPED_ERROR_KEY;
    }
}
