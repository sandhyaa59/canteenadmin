package org.grow.canteen.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.dto.product.ProductAddRequest;
import org.grow.canteen.dto.product.ProductByCategoryResponse;
import org.grow.canteen.dto.product.ProductListResponse;
import org.grow.canteen.dto.product.UpdateProductRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(Route.PRODUCT)
    private Response saveProduct(@Valid @RequestBody ProductAddRequest request){
        log.info("Saving Product::{{}}",request);
        return productService.addProducts(request);
    }

    @GetMapping(Route.PRODUCT_LIST)
    private PageableData<List<ProductListResponse>> getAllProduct(@Valid Pagination pagination){
        log.info("fetching all product::{{}}",pagination);
        return productService.getAllProducts(pagination);
    }
    @GetMapping(Route.ACTIVATE_PRODUCT)
    private Response activateProduct(@PathVariable("id") Long id){
        log.info("activate  product::{}",id);
        return productService.activateProduct(id);
    }

    @GetMapping(Route.DEACTIVATE_PRODUCT)
    private Response deActivateProduct(@PathVariable("id") Long id){
        log.info("deactivate  product::{}",id);
        return productService.deActivateProduct(id);
    }

    @GetMapping(Route.DELETE_PRODUCT)
    private Response deleteProduct(@PathVariable("id") Long id){
        log.info("Delete  product::{}",id);
        return productService.deleteProduct(id);
    }

    @PostMapping (Route.UPDATE_PRODUCT)
    private Response updateProduct(@Valid @RequestBody UpdateProductRequest request){
        log.info("Deactivate  product::{{}}",request);
        return productService.updateProduct(request);
    }

    @GetMapping(Route.SEARCH_PRODUCT)
    private PageableData<List<ProductListResponse>> searchProduct(@PathVariable("keyword") String keyword, @Valid Pagination pagination){
        log.info("Search  product::{}",keyword);
        return productService.searchProduct(pagination, keyword);
    }

    @GetMapping(Route.CATEGORISED_PRODUCT)
    private List<ProductByCategoryResponse> getProductWithCategory(){
        log.info("Get  product with Category::");
        return productService.getProductByCategory();
    }
}
