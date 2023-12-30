package org.grow.canteen.services;

import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.dto.product.ProductByCategoryResponse;
import org.grow.canteen.dto.product.ProductListResponse;

import org.grow.canteen.dto.product.ProductAddRequest;
import org.grow.canteen.dto.product.UpdateProductRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.entities.Product;

import java.util.List;

public interface ProductService {

    Response addProducts(ProductAddRequest request);
    PageableData<List<ProductListResponse>> getAllProducts(Pagination pagination);
    Response activateProduct(Long id);
    Response deActivateProduct(Long id);

    Product validateProductById(Long id);

    Response updateProduct(UpdateProductRequest request);

    Response deleteProduct(Long id);
    PageableData<List<ProductListResponse>> searchProduct(Pagination pagination, String keyword);
    List<ProductByCategoryResponse> getProductByCategory();


}
