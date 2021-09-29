package com.revature.put_sets.models;
import lombok.Data;

import java.util.List;


@Data
public class SetDto {

    private String set_name;
    private List<String> tags;
    private boolean is_public;

}