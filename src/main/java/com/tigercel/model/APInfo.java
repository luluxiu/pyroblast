package com.tigercel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by freedom on 2016/3/29.
 */
@Entity
@Data
@Table(name = "t_apinfo")
@JsonIgnoreProperties(value = {"rule", "group", "connection"})
public class APInfo extends BaseModel {

    @Column(nullable = false, unique=true, length = 32)
    private String name;

    @Column(length = 256)
    private String description;

    @Column(nullable = false, unique=true, length=18)
    private String mac;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rule rule;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ap", cascade = CascadeType.REMOVE)
    private Collection<Connection> connection = new ArrayList<>();

    @Column(nullable = false)
    private Date updatedAt;


    private Date lastHeartbeatAt;

    @Column(length = 16)
    private String lastHeartbeatIp;

    private Long lastHeartbeatSysTime;

    private Long lastHeartbeatSysMemfree;

    private Float lastHeartbeatSysLoad;

    private Long lastHeartbeatWifidogUptime;


    public void setUpdatedAt() {
        updatedAt = new Date();
    }

}
