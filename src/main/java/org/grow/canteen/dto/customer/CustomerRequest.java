package org.grow.canteen.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CustomerRequest {

    @NotBlank(message = "Customer Name cannot be blank")
    private String name;
    private String address;
    private String email;
    @Size(min = 8,max = 14,message = "Invalid Mobile Number  ")
    private String mobileNumber;
}
