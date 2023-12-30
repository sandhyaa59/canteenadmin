package org.grow.canteen.dto.Category;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class CategoryAddResponse {
    public String name;


    public Long id;
}
