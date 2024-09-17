package com.api.shops.service.category;

import com.api.shops.model.Category;
import com.api.shops.request.category.AddCategoryRequest;
import com.api.shops.request.category.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long categoryId);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(AddCategoryRequest request);
    Category updateCategory(Long categoryId, UpdateCategoryRequest request);
    void deleteCategory(Long categoryId);
}
