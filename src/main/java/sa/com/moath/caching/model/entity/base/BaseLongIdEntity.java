package sa.com.moath.caching.model.entity.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseLongIdEntity extends AuditEntity implements Serializable {

    @Id
    @Getter
    @Column (name ="id" , unique = true, nullable = false, columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final BaseLongIdEntity other = (BaseLongIdEntity) obj;
        if (getId() == null) {
            return other.getId() == null;
        } else return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                getId());
    }

}
