package org.grow.canteen.dto.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateAdminRequest {

    @NotNull(message = "Admin Id cannot be null")
    private Long id;

    private String fullName;

//    private String email;
    @Size(min = 9,max = 14,message = "Invalid Mobile Number")
    private String mobileNumber;
//    @Size(min=6,message = "Password should be greater than 6 Characters")
    private String password;
}
