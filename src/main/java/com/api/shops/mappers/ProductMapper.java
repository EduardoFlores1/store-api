package com.api.shops.mappers;

import com.api.shops.model.Product;
import com.api.shops.request.product.AddProductRequest;
import com.api.shops.request.product.UpdateProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product addToModel(AddProductRequest request);
    Product updateToModel(UpdateProductRequest request);
}
