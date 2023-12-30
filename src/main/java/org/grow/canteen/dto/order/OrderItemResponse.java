package org.grow.canteen.dto.order;


import lombok.Getter;
import lombok.Setter;

@Setter
public class OrderItemResponse {

    public Long id;
    public Long productId;
    public String productName;
    public double rate;
    public double totalPrice;
    public double quantity;
}
