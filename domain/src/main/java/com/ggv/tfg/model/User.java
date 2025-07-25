package com.ggv.tfg.model;

import com.ggv.tfg.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String name;
    private String password;
    private String email;
    private RoleEnum role;

}
