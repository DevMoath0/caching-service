package sa.com.moath.caching.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sa.com.moath.caching.model.entity.base.BaseLongIdEntity;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseLongIdEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private Long age;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email")
    private String email;

}

