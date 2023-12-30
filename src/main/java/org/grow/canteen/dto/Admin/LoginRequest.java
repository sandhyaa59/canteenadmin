package org.grow.canteen.dto.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "Username cannot be empty")
    private String userName;
    @Size(min=6,max = 255,message = "Invalid Password Length")
    private String password;
}
