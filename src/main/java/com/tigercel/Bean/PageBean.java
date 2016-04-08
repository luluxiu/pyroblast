package com.tigercel.Bean;



import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageBean {

    @NotNull
    private int page;

    @NotNull
    private int size;

    private String sortName = "updatedAt";

    private String sortOrder = "asc";
}
