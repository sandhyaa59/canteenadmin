package org.grow.canteen.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String customerName;

    @Column(name = "address")
    private String address;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @Column(name = "emailId")
    private String emailId;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_on",nullable = false,updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "created_by")
    private  String createdBy;

}
