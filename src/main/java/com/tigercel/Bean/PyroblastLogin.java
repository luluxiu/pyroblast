package com.tigercel.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by freedom on 2016/4/1.
 */
@Data
public class PyroblastLogin implements Serializable {

    @NotBlank
    @Length(min = 1, max = 32)
    private String gw_id;

    @NotBlank
    @Pattern(regexp = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$",
            message="Invalid ip address")
    private String gw_address;

    @NotNull
    private int gw_port;

    @NotBlank
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String mac;

    @JsonIgnore
    private String url;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;
}
