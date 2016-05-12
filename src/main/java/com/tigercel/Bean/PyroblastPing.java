package com.tigercel.Bean;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by freedom on 2016/4/1.
 */
@Data
public class PyroblastPing implements Serializable {

    @NotBlank
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String gw_id;

    @NotNull
    private Long sys_uptime;

    @NotNull
    private Long sys_memfree;

    @NotNull
    private float sys_load;

    @NotNull
    private Long wifidog_uptime;
}
