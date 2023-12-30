package org.grow.canteen.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@AllArgsConstructor
public class ProductByCategoryResponse {
    public String categoryName;
    public Long categoryId;
    public  List<ProductListResponse> products;


}
