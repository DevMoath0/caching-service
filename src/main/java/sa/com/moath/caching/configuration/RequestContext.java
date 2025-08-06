package sa.com.moath.caching.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class RequestContext implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;

}
