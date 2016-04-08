package com.tigercel.Bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by freedom on 2016/3/31.
 */
@Data
public class APBean {

    private Long id;

    @NotBlank
    @Length(min = 1, max = 32)
    private String name;

    @Length(min = 1, max = 256)
    private String description;

    @NotBlank
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String mac;

    private Long groupId = new Long(0);

}
