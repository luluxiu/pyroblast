package com.tigercel.Bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by freedom on 2016/3/31.
 */
@Data
public class GroupBean {

    private Long id;

    @NotNull
    @Length(min = 1, max = 32)
    private String name;

    @Length(min = 1, max = 256)
    private String description;

    private Long ruleId = new Long(0);

}
