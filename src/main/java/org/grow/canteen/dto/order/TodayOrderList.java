package org.grow.canteen.dto.order;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class TodayOrderList {

    public Long id;
    public String token;
    public double total;
    public double subtotal;
    public String customer;
    public LocalDateTime addedDateTime;

}
