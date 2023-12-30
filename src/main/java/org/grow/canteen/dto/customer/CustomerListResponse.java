package org.grow.canteen.dto.customer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class CustomerListResponse {

    public Long id;
    public String name;
    public String address;
    public String email;

    public String mobileNumber;
    public boolean isActive;


    public boolean isDeleted;


    public LocalDateTime createdOn;


    public LocalDateTime modifiedOn;


    public   String createdBy;
}
