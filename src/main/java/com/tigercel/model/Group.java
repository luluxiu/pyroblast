package com.tigercel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by freedom on 2016/3/29.
 */
@Entity
@Data
@Table(name = "t_group")
@JsonIgnoreProperties(value = {"rule", "apinfos"})
public class Group extends BaseModel {

    @Column(nullable = false, unique=true, length = 32)
    private String name;

    @Column(length = 256)
    private String description;

    @Transient
    private String ruleName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rule rule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.REMOVE)
    private Collection<APInfo> apinfos = new ArrayList<>();
}
