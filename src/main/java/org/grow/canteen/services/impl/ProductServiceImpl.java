package org.grow.canteen.services.impl;

import jakarta.transaction.Transactional;
import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.commons.exceptions.RestException;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.dto.product.*;
import org.grow.canteen.dto.product.ProductListResponse;
import org.grow.canteen.dto.Response;
import org.grow.canteen.entities.*;
import org.grow.canteen.entities.Product;
import org.grow.canteen.repository.ProductRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.services.ProductService;
import org.grow.canteen.services.CategoryService;
import org.grow.canteen.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    private ContextHolderService contextHolderService;
    private ProductRepository productRepository;
    private CategoryService categoryService;

    private AdminService adminService;

    @Autowired
    public ProductServiceImpl( ContextHolderService contextHolderService,
                              ProductRepository productRepository,CategoryService categoryService,
                              AdminService adminService) {

        this.contextHolderService = contextHolderService;
        this.productRepository = productRepository;
        this.categoryService=categoryService;
        this.adminService=adminService;
    }

    @Transactional
    @Override
    public Response addProducts(ProductAddRequest request) {
     adminService.validateAdmin (contextHolderService.getContext().getUserId());
        Category category=categoryService.validateCategory(request.getCategoryId());
        System.out.print(category.getId());
        Product product=prepareToAddProduct(request);
        product.setCategoryId(category);
        Product response=productRepository.save(product);
        return Response.builder().id(response.getId()).build();
    }


    private PageableData<List<ProductListResponse>> getProductListResponses(Pagination pagination) {
        List<ProductListResponse> ProductListResponseList=new ArrayList<>();
        Pageable pageable = PaginationUtil.performPagination(pagination);
        Page<Product> ProductList=productRepository.getAllProduct(pageable);
        ProductList.forEach(product -> {
            ProductListResponse ProductListResponse=prepareToSendProductListResponse(product);
            ProductListResponseList.add(ProductListResponse);
        });
        return new PageableData<>(ProductListResponseList, ProductList.getTotalPages(), ProductList.getTotalElements(), pagination.getPage());
    }
    private ProductListResponse prepareToSendProductListResponse(Product product){
        ProductListResponse response=new ProductListResponse();
        response.setId(product.getId());
        response.setTitle(product.getTitle());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setAddedDate(product.getCreatedOn());
        response.setActive(product.isActive());
        response.setCategoryId(product.getCategoryId().getId());
        response.setCategoryName(product.getCategoryId().getName());
        return response;
    }

    @Override
    public PageableData<List<ProductListResponse>> getAllProducts(Pagination pagination) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());

        return getProductListResponses(pagination);
    }

    @Override
    public Response activateProduct(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Product activeProduct=validateProductforActiveDeactive(id);
        activeProduct.setActive(true);
        activeProduct.setModifiedOn(LocalDateTime.now());
        productRepository.save(activeProduct);
        return Response.builder().id(activeProduct.getId()).build();
    }

    private   Product validateProductforActiveDeactive(Long id){
        Optional<Product> productOptional=productRepository.validateProductByIdForActive(id);
        if (productOptional.isEmpty()){
            throw new RestException("P003","Product doesnot exists");
        }else {
            return productOptional.get();
        }
    }

    @Override
    public Response deActivateProduct(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Product activeProduct=validateProductforActiveDeactive(id);
        activeProduct.setActive(false);
        activeProduct.setModifiedOn(LocalDateTime.now());
        productRepository.save(activeProduct);
        return Response.builder().id(activeProduct.getId()).build();
    }

    @Override
    public Product validateProductById(Long id) {
        Optional<Product> productOptional=productRepository.validateProductById(id);
        if (productOptional.isPresent()){
            return productOptional.get();
        }else {
            throw new RestException("P001","Product not found");
        }
    }

    @Override
    public Response updateProduct(UpdateProductRequest request) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
       Product product= validateProductById(request.getProductId());
        prepareToUpdateProduct(product, request);
        Category category=categoryService.validateCategory(request.getCategoryId());
       product.setCategoryId(category);
       productRepository.save(product);
       return Response.builder().id(product.getId()).build();

    }

    @Override
    public Response deleteProduct(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Product product= validateProductById(id);
        product.setDeleted(true);
        product.setModifiedOn(LocalDateTime.now());
        productRepository.save(product);
        return Response.builder().id(product.getId()).build();
    }

    @Override
    public PageableData<List<ProductListResponse>> searchProduct(Pagination pagination, String keyword) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        List<ProductListResponse> productListResponses=new ArrayList<>();
        Pageable pageable = PaginationUtil.performPagination(pagination);
        Page<Product> products=productRepository.searchProduct(pageable,keyword);
        products.forEach(product -> {
            ProductListResponse toSendProductListResponse=prepareToSendProductListResponse(product);
            productListResponses.add(toSendProductListResponse);
        });
        return new PageableData<>(productListResponses, products.getTotalPages(), products.getTotalElements(), pagination.getPage());
    }

    @Override
    public List<ProductByCategoryResponse> getProductByCategory() {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        List<ProductListResponse> productListResponses=new ArrayList<>();

        List<Product> products=productRepository.getAllActiveProducts();
        products.forEach(product -> {
            ProductListResponse toSendProductListResponse=prepareToSendProductListResponse(product);
            productListResponses.add(toSendProductListResponse);
        });
        Map<Long,List<ProductListResponse>> response= productListResponses.stream()
                .collect(Collectors.groupingBy(ProductListResponse::getCategoryId));

        return response.entrySet().stream()
                .map(entry -> new ProductByCategoryResponse(entry.getValue().get(0).getCategoryName(), entry.getKey(), entry.getValue()))
                .toList();
    }

    private void prepareToUpdateProduct(Product product, UpdateProductRequest request){
        product.setDescription(request.getDescription());
        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        product.setModifiedOn(LocalDateTime.now());
    }

   private Product prepareToAddProduct(ProductAddRequest request){
        Product product=new Product();
        product.setActive(true);
        product.setDeleted(false);
        product.setDescription(request.getDescription());
        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        product.setCreatedBy(contextHolderService.getContext().getUserName());
        product.setModifiedOn(LocalDateTime.now());
        product.setCreatedOn(LocalDateTime.now());
return product;
    }
}
