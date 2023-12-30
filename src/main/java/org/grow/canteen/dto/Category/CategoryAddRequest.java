package org.grow.canteen.dto.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryAddRequest {
    @NotBlank(message = "Category Name is Empty")
    private String name;

    @Size(message = "Description should be less than 255 Characters",max = 255)
    private  String description;

}
