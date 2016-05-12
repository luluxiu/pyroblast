package com.tigercel.Bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Created by freedom on 2016/3/31.
 */
@Data
public class RuleBean implements Serializable {

    private Long id = new Long(0);

    @NotNull
    @Length(min = 1, max = 32)
    private String name;

    @Length(min = 1, max = 256)
    private String description;

    @NotNull
    private Boolean wander = true;


    @Length(min = 1, max = 64)
    private String loginPage;

    @Length(min = 1, max = 64)
    private String portalPage;

    @Min(value = 5)
    private Integer authExpirationTime;
}
