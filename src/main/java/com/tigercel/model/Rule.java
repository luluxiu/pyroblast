package com.tigercel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by freedom on 2016/3/29.
 */
@Entity
@Data
@Table(name = "t_rule")
@JsonIgnoreProperties(value = {"groups", "apinfos"})
public class Rule extends BaseModel {

    @Column(nullable = false, unique=true, length = 32)
    private String name;

    @Column(length = 256)
    private String description;

    @Column(nullable = false)
    private Boolean wander = true;

    @Column(length = 64)
    private String loginPage;

    @Column(length = 64)
    private String portalPage;

    @Min(value = 5)
    private Integer authExpirationTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rule")
    private Collection<Group> groups = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rule")
    private Collection<APInfo> apinfos = new ArrayList<>();
}
