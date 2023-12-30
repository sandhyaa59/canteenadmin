package org.grow.canteen.dto.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryUpdateRequest {

    @NotNull(message = "Category Id cannot be null")
    public Long id;
    @NotBlank(message = "Category Name cannot be blank")
    public String name;
    public  String description;


}
