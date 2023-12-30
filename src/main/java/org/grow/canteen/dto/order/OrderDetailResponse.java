package org.grow.canteen.dto.order;

import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
public class OrderDetailResponse {
    public Long id;
    public LocalDateTime createdDateTime;
    public double total;
    public double subtotal;
    public String paymentMethod;

    public String customerName;

    public double discountAmount;

    public String token;

    public List<OrderItemResponse> itemResponse;
}
