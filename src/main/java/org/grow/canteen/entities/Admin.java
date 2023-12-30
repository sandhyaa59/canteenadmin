package org.grow.canteen.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "admins") //table name is admins
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;


    @Column(name = "full_name",length = 50)
    private String fullName;

    @Column(name = "email_id",length = 50,unique = true)
    private String emailId;

    @Column(name = "mobile_number",length = 50)
    private String mobileNumber;

    @Column(name = "password",length = 255)
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "maker",length = 50)
    private  String maker;

    @Column(name = "checker",length = 50)
    private String checker;

    @Column(name = "deleted_by",length = 50)
    private  String deletedBy;

    @Column(name = "created_on",nullable = false,updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
}
