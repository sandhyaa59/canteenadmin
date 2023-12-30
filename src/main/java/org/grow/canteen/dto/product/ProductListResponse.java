package org.grow.canteen.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

public class ProductListResponse {

    public Long id;
    public String title;
    public String description;
    public String image;
    public double price;
    public LocalDateTime addedDate;
    public boolean isActive;
    public boolean isDeleted;
    public Long categoryId;
    public String categoryName;


}
