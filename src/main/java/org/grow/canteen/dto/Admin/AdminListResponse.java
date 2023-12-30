package org.grow.canteen.dto.Admin;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class AdminListResponse {

    public Long id;
    public String fullName;
    public String email;
    public String mobileNumber;
    public LocalDateTime createdAt;
    public boolean isActive;


}
