package com.fxlabs.issues.dao.entity.users;

import com.fxlabs.issues.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fx-users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseEntity {

    private String name;

    @NotNull(message = "Username is required.")
    @NotBlank(message = "Email is required.")
    @Size(max = 64)
    private String username;

    @NotNull(message = "Email is required.")
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is not a valid format")
    private String email;

    private String password;

    @ElementCollection
    private List<String> privileges;

    private String company;
    private String location;
    private String jobTitle;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

}

