package org.grow.canteen.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private double total;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "discont_amount")
    private double discountAmount;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "order_dateTime")
    private String orderDateTime;

    @Column(name = "token_no")
    private String tokenNo;

    @Column(name = "token_value")
    private int tokenValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "is_void")
    private boolean isVoid;

   @Column(name = "customer_name")
    private String customerName;


}
