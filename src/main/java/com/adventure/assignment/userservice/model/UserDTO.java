package com.adventure.assignment.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    @NotNull(message = "userName is mandatory")
    private String userName;

    @NotNull(message = "email is mandatory")
    private String email;

    @NotNull(message = "pwd is mandatory")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    @NotNull(message = "role is mandatory")
    private String role;
}
