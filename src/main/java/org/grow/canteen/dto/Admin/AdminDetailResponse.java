package org.grow.canteen.dto.Admin;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder
public class AdminDetailResponse {
    public  Long id;



    public String fullName;


    public String emailId;


    public String mobileNumber;

    public boolean isActive;

    public  String maker;


    public String checker;


    public  String deletedBy;


    public LocalDateTime createdOn;


    public LocalDateTime modifiedOn;
}
