package com.tigercel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.model.support.UserType;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by freedom on 2016/3/29.
 */
@Entity
@Data
@Table(name = "t_user")
@JsonIgnoreProperties(value = {"password"})
public class User extends BaseModel {

    @Column(nullable = false, unique=true, length = 32)
    private String name;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type = UserType.GUEST;

    @Column(length = 256)
    private String description;

}
