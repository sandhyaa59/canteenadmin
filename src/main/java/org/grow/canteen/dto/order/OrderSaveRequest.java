package org.grow.canteen.dto.order;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderSaveRequest {

    @NotNull(message = "Total cannot be null")
    private double total;
    @NotNull(message = "Subtotal cannot be null")
    private double subTotal;
    @NotNull(message = "Order Date Time cannot be null")
    private String orderDateTime;
    @NotBlank(message = "Payment Mode cannot be blank")
    private String paymentMode;

    private String customerName;

    private double discountAmount;

    @NotNull(message = "Order Item cannot be null")
    private List<OrderItemRequest> orderItem;
}
