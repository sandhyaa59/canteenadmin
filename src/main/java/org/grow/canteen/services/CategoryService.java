package org.grow.canteen.services;

import org.grow.canteen.dto.Category.CategoryAddRequest;
import org.grow.canteen.dto.Category.CategoryAddResponse;
import org.grow.canteen.dto.Category.CategoryListtResponse;
import org.grow.canteen.dto.Category.CategoryUpdateRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.entities.Category;

import java.util.List;

public interface CategoryService {

    CategoryAddResponse saveCategory(CategoryAddRequest request);

    Category validateCategory(Long id);
    Response updateCategory(CategoryUpdateRequest request);

    Response activeCategory(Long id);

    Response deActivateCategory(Long id);

    List<CategoryListtResponse> categoryList();

    Response deleteCategory(Long id);
}
