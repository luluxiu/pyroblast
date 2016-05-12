package com.tigercel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.Constants;
import com.tigercel.model.support.AuthStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Created by freedom on 2016/3/29.
 */
@Entity
@Data
@Table(name = "t_connection")
@JsonIgnoreProperties(value = {"ap"})
public class Connection extends BaseModel {

    @Column(length = 18)
    private String mac;

    @Column(length = 16)
    private String ip;

    private Long outgoing;

    private Long incoming;

    private Date loginTime;

    @Min(value = 0)
    private Integer loginCount = 0;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthStatus status = AuthStatus.VALIDATING;

    @ManyToOne(fetch = FetchType.LAZY)
    private APInfo ap;

    private Date expiredAt;

    private String client = Constants.CONNECTION_CLIENT_BROWSER;
}
