package com.mavuzer.authentication.users.dto;

import com.mavuzer.authentication.role.model.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class UserCreateDto {

    @NotEmpty(message = "User name is mandatory.")
    @Size(min = 5, max = 10, message = "User name should have min 5 max 10 characters.")
    private String userName;

    @NotBlank(message = "Firstname is mandatory field!")
    @Size(max = 50, message = "Firstname should be smaller than {max} characters")
    private String firstName;

    @NotBlank(message = "Lastname is mandatory field!")
    @Size(max = 50, message = "Firstname should be smaller than {max} characters")
    private String lastName;

    @NotBlank(message = "E-mail is mandatory field!")
    @Size(max = 50, message = "E-mail should be smaller than {max} characters")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory field!")
    @Size(max = 200, message = "Password should be smaller than {max} characters")
    private String password;

    private Set<Role> roles;
}
