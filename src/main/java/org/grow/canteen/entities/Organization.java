package org.grow.canteen.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "Organization name",length = 50)
    public String organizationName;
    @Column(name = "address",length = 50)
    public String address;


    @Column(name = "createdBy",length = 50)
    public String createdBY;

    @Column(name = "organization deatils",length = 50)
    public String organization_details;

    @Column(name = "pan_number",length = 50)
    public String pan_Number;

    @Column(name = "mobile_number",length = 50)
    public  String mobile_number;

    @Column(name = "email",length = 50)
    public String emailID;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "logo")
    public  String logo;





}
