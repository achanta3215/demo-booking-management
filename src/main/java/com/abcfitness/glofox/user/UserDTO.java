package com.abcfitness.glofox.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @Email(message = "Email should be valid")
    private String email;
}
