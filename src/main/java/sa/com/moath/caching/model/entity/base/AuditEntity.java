package sa.com.moath.caching.model.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sa.com.moath.caching.configuration.ContextHolder;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Getter
    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "modified_by")
    protected String modifiedBy;

    @Getter
    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        createdBy = ContextHolder.get().getUserId();
    }

    @PreUpdate
    protected void onPersist() {
        modifiedDate = LocalDateTime.now();
        modifiedBy = ContextHolder.get().getUserId();
    }

    @JsonIgnore
    public String getCreatedBy() {
        return createdBy;
    }

}
