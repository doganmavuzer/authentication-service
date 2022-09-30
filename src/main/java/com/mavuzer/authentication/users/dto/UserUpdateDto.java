package com.mavuzer.authentication.users.dto;

import com.mavuzer.authentication.role.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserUpdateDto {

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
