package org.grow.canteen.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.Admin.AdminListResponse;
import org.grow.canteen.dto.Admin.UpdateAdminRequest;
import org.grow.canteen.dto.Category.CategoryAddRequest;
import org.grow.canteen.dto.Category.CategoryAddResponse;
import org.grow.canteen.dto.Category.CategoryListtResponse;
import org.grow.canteen.dto.Category.CategoryUpdateRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(Route.CATEGORY)
    private CategoryAddResponse saveCategory(@Valid @RequestBody CategoryAddRequest request){
        log.info("Saving admin::{{}}",request);
        return categoryService.saveCategory(request);
    }
    @PostMapping(Route.UPDATE_CATEGORY)
    private Response updateCategoryDetails(@Valid @RequestBody CategoryUpdateRequest request){
        log.info("Update admin::{{}}",request);
        return categoryService.updateCategory(request);
    }

    @GetMapping(Route.Active_CATEGORY)
    private Response activeCategory(@PathVariable("id") Long id){
        log.info("activate admin::{}",id);
        return categoryService.activeCategory(id);
    }

    @GetMapping(Route.DEACTIVATE_CATEGORY)
    private Response deactivateCategory(@PathVariable("id") Long id){
        log.info("Deactivate admin::{}",id);
        return categoryService.deActivateCategory(id);
    }

    @GetMapping(Route.CATEGORY)
    private List<CategoryListtResponse> fetchCategory(){
        log.info("Fetching Category::");
        return categoryService.categoryList();
    }

    @GetMapping(Route.DELETE_CATEGORY)
    private Response deleteCategory(@PathVariable("id") Long id){
        log.info("Delete Category::{}",id);
        return categoryService.deleteCategory(id);
    }
}
