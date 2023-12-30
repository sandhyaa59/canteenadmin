package org.grow.canteen.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductRequest {

    @Size(max = 50,message = "Title should be less than 50 Characters")
    private String title;
    @Size(max = 500,message = "Description should be less than 500 Characters")
    private String description;
    @NotNull(message = "Price cannot be empty")
    private double price;

    @NotNull(message = "Category Id cannot be null")
    private Long categoryId;

    @NotNull(message = "Product Id cannot be null")
    private Long productId;
}
