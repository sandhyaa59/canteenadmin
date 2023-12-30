package org.grow.canteen.dto.productScheduler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateRequest {

    @NotNull(message = "Scheduler Id cannot be null")
    private Long id;

    @NotNull(message = "Product Id cannot be null")
    private Long productId;

    @NotBlank(message = "Days cannot be blank")
    private String days;
}
