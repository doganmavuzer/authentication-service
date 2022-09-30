package com.mavuzer.authentication.users.model;

import com.mavuzer.authentication.role.model.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Document(collection = "users")
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    private String id;

    @NotEmpty(message = "User name is mandatory.")
    @Size(min = 5, max = 10, message = "User name should have min 5 max 10 characters.")
    @Indexed(unique = true)
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
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory field!")
    @Size(max = 200, message = "Password should be smaller than {max} characters")
    private String password;

    @DBRef
    private Set<Role> roles;


    @Builder.Default
    private boolean accountNonExpired = false;

    @Builder.Default
    private boolean isEnabled = true;


}
