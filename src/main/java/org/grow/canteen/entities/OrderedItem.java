package org.grow.canteen.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordered_item")
@Data
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "rate")
    private double rate;

    @Column(name = "added_time")
    private LocalDateTime addedTime;

}
