package org.grow.canteen.dto.Admin;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminAddRequest {

    @NotBlank(message = "Full Name cannot be Empty")
    private String fullName;
    @NotBlank(message = "Email cannot be Empty")
    private String email;
    @Size(min = 9,max = 14,message = "Invalid Mobile Number")
    private String mobileNumber;
    @Size(min=6,message = "Password should be greater than 6 Characters")
    private String password;
}
