package com.fxlabs.fxt.dao.entity.users;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseEntity<String> {


    private String name;
    @NotNull(message = "Username is required.")
    @NotBlank(message="Email is required.")
    @Size(max=64)
    private String username;
    @NotNull(message="Email is required.")
    @NotBlank(message="Email is required.")
    @Email(message = "Email is not a valid format")
    private String email;
    private String company;
    private String location;
    private String jobTitle;
    //private Org org;


}

