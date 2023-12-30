package org.grow.canteen.dto.productScheduler;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ProductSchedulerListResponse {

    public Long id;
    public String day;
    public String productName;
    public String description;
    public double price;
    public String addedBy;
    public Long productId;
    public LocalDateTime addedTime;
    public LocalDateTime modifiedOn;
    public boolean isActive;

}
