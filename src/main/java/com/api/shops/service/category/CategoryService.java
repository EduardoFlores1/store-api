package com.api.shops.service.category;

import com.api.shops.exceptions.AlreadyExistsException;
import com.api.shops.exceptions.CategoryNotFoundException;
import com.api.shops.mappers.CategoryMapper;
import com.api.shops.model.Category;
import com.api.shops.repository.CategoryRepository;
import com.api.shops.request.category.AddCategoryRequest;
import com.api.shops.request.category.UpdateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(AddCategoryRequest request) {
        return Optional.of(request)
                .filter(c -> !categoryRepository.existByName(c.getName()))
                .map(c -> categoryRepository.save(categoryMapper.addToEntity(c)))
                .orElseThrow(() -> new AlreadyExistsException(request.getName() + " already exist!"));
    }

    @Override
    public Category updateCategory(Long categoryId, UpdateCategoryRequest request) {
        return categoryRepository.findById(categoryId)
                .map(existCategory -> {
                    existCategory.setName(request.getName());
                    return categoryRepository.save(existCategory);
                })
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found!"));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        () -> {throw new CategoryNotFoundException("Category Not Found!");}
                );
    }
}
