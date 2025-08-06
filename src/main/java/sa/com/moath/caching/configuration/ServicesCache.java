package sa.com.moath.caching.configuration;

import lombok.Getter;

import java.time.Duration;
import java.util.Objects;

@Getter
public class ServicesCache {
    private String name;
    private Duration expireDuration;
    private int initialCapacity = 10;
    private int maximumSize = 50;

    public void setName(String name) {
        Objects.requireNonNull(expireDuration);
        this.name = name;
    }

    public void setExpireDuration(Duration expireDuration) {
        Objects.requireNonNull(expireDuration);
        if (expireDuration.toMillis() < 0) {
            throw new IllegalArgumentException(
                    "expireDuration must be greater than or equal to 0.");
        }
        this.expireDuration = expireDuration;
    }

    public void setInitialCapacity(int initialCapacity) {
        Objects.requireNonNull(initialCapacity);
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "initialCapacity must be greater than or min to 1.");
        }
        this.initialCapacity = initialCapacity;
    }

    public void setMaximumSize(int maximumSize) {
        Objects.requireNonNull(maximumSize);
        if (maximumSize < 10) {
            throw new IllegalArgumentException(
                    "maximumSize must be greater than or min to 10.");
        }
        this.maximumSize = maximumSize;
    }

}