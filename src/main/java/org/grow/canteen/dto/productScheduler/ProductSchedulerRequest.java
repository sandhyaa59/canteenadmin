package org.grow.canteen.dto.productScheduler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductSchedulerRequest {

    @NotNull(message = "Product Id cannot be null")
    private List<Long> productId;

    @NotBlank(message = "Days cannnot be blank")
    private String days;

}
