package com.api.shops.mappers;

import com.api.shops.model.Category;
import com.api.shops.request.category.AddCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category addToEntity(AddCategoryRequest request);
}
