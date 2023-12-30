package org.grow.canteen.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.grow.canteen.constants.Days;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product_scheduler")
public class ProductScheduler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private Days days;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

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
