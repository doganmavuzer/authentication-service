package com.mavuzer.authentication.role.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String id;

    @NotBlank(message = "Role name is a mandatory field!")
    @Size(min = 1, max = 50, message = "Role name should between {min} and {max} characters")
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
