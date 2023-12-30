package org.grow.canteen.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderItemRequest {
    @NotNull(message = "Quantity cannot be null")
    private double quantity;
    @NotNull(message = "price cannot be null")
    private double price;
    @NotNull(message = "Product Id cannot be null")
    private Long productId;

    @NotNull(message = "Total Price cannot be null")
    private double totalPrice;
}
