package com.mavuzer.authentication.users.dto;

import com.mavuzer.authentication.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public UserDto(User user) {
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

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

}
