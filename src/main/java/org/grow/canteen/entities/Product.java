package org.grow.canteen.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",length = 50)
    private String title;

    @Column(name = "description",length = 500)
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image",length = 500)
    private String image;

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

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category categoryId;

}
