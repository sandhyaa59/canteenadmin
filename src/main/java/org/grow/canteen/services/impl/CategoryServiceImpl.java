package org.grow.canteen.services.impl;

import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.commons.exceptions.RestException;
import org.grow.canteen.dto.Category.CategoryAddRequest;
import org.grow.canteen.dto.Category.CategoryAddResponse;
import org.grow.canteen.dto.Category.CategoryListtResponse;
import org.grow.canteen.dto.Category.CategoryUpdateRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.entities.Category;
import org.grow.canteen.entities.Product;
import org.grow.canteen.repository.CategoryAddRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryAddRepository categoryAddRepository;
    private ContextHolderService contextHolderService;
    private AdminService adminService;

    @Autowired
    public CategoryServiceImpl(CategoryAddRepository categoryAddRepository, AdminService adminService,
                               ContextHolderService contextHolderService) {
        this.categoryAddRepository = categoryAddRepository;
        this.contextHolderService = contextHolderService;
        this.adminService=adminService;
    }


    public CategoryAddResponse saveCategory(CategoryAddRequest request) {
        Category category = prepareToSaveCategory(request);
        Category response = categoryAddRepository.save(category);

        return CategoryAddResponse.builder().id(response.getId()).name(response.getName()).build();
    }

    @Override
    public Category validateCategory(Long id) {
        Optional<Category> category=categoryAddRepository.validateCategoryByIdForActive(id);
        if(category.isPresent()){
            return  category.get();
        }else {
            throw new RestException("C001","Category doesnot exists");
        }

    }

    @Override
    public Response updateCategory(CategoryUpdateRequest request) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Category category= validateCategoryforActiveDeactive(request.getId());
        category.setModifiedOn(LocalDateTime.now());
        category.setName(request.getName());
        category.setDescription(request.getDescription());
       Category category1= categoryAddRepository.save(category);
        return Response.builder().id(category1.getId()).build();
    }

    @Override
    public Response activeCategory(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Category category=validateCategoryforActiveDeactive(id);
        category.setActive(true);
        category.setModifiedOn(LocalDateTime.now());
        categoryAddRepository.save(category);
        return  Response.builder().id(category.getId()).build();

    }

    @Override
    public Response deActivateCategory(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Category activeCategory=validateCategoryforActiveDeactive(id);
        activeCategory.setActive(false);
        activeCategory.setModifiedOn(LocalDateTime.now());
        categoryAddRepository.save(activeCategory);
        return Response.builder().build();
    }


    private   Category validateCategoryforActiveDeactive(Long id){
        Optional<Category> categoryOptional=categoryAddRepository.validateCategoryByIdForActive(id);
        if (categoryOptional.isEmpty()){
            throw new RestException("C003","Category doesnot exists");
        }else {
            return categoryOptional.get();
        }
    }



    @Override
    public List<CategoryListtResponse> categoryList() {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        List<Category> categoryList=categoryAddRepository.getAllCategory();
        List<CategoryListtResponse> categoryListtResponses=new ArrayList<>();
        categoryList.forEach(category -> {
            CategoryListtResponse categoryListtResponse=prepareToSendCategoryResponse(category);
            categoryListtResponses.add(categoryListtResponse);
        });
        return categoryListtResponses;
    }

    @Override
    public Response deleteCategory(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
       Category category= validateCategory(id);
       category.setDeleted(true);
       category.setModifiedOn(LocalDateTime.now());
       categoryAddRepository.save(category);
       return Response.builder().id(category.getId()).build();
    }

    private CategoryListtResponse prepareToSendCategoryResponse(Category category){
        CategoryListtResponse response=new CategoryListtResponse();
        response.setActive(category.isActive());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setId(category.getId());
        response.setCreatedBy(category.getCreatedBy());
       return  response;
    }


    Category prepareToSaveCategory(CategoryAddRequest request) {
        Category category = new Category();
        category.setActive(true);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setActive(true);
        category.setDeleted(false);
        category.setModifiedOn(LocalDateTime.now());
        category.setCreatedOn(LocalDateTime.now());
        category.setCreatedBy(contextHolderService.getContext().getUserName());
        return category;
    }
}
