package org.grow.canteen.dto.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationAddRequest {

    @NotBlank(message = "Organization Name cannot be blank")
    @Size(max = 50,message = "Organization Name should be less than 50 Characters")
    private String name;
    @Size(max = 500,message = "Address should be less than 500 Characters")
    private String address;
    @Size(max = 500,message = "Contact Number should be less than 10 digits")
    private String contactNumber;

    @Size(max = 500,message = "Enter valid Email Address")
    private String email_Id;


}
