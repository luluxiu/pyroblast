package com.tigercel.Bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by freedom on 2016/3/31.
 */
@Data
public class GroupBean implements Serializable {

    private Long id = new Long(0);

    @NotNull
    @Length(min = 1, max = 32)
    private String name;

    @Length(min = 1, max = 256)
    private String description;

    private String ruleName;

}
