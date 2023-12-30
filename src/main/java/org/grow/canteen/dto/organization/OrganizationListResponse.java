package org.grow.canteen.dto.organization;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class OrganizationListResponse {
    public Long id;
    public String name;
    public String address;
    public String createdBy;
    public String contactNumber;
    public  String panNumber;

}
