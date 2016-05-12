package com.tigercel.Bean;

import com.tigercel.Constants;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by freedom on 2016/4/1.
 */
@Data
public class PyroblastAuth implements Serializable {

    @NotBlank
    private String stage;

    @NotBlank
    @Pattern(regexp = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$",
            message="Invalid ip address")
    private String ip;

    @NotBlank
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String mac;

    @NotBlank
    private String token;

    @NotNull
    private Long incoming;

    @NotNull
    private Long outgoing;

    @NotBlank
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String gw_id;

    private String client = Constants.CONNECTION_CLIENT_BROWSER;
}
