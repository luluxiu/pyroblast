package com.tigercel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by freedom on 2016/4/11.
 */
@Entity
@Data
@Table(name = "t_pyroblast_user")
//@JsonIgnoreProperties(value = {"password"})
public class PyroblastUser extends BaseModel {

    @Column(nullable = false, unique = true, length = 32)
    private String username;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(length = 256)
    private String description;

}
