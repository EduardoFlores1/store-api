package com.api.shops.service.product;

import com.api.shops.model.Product;
import com.api.shops.request.product.AddProductRequest;
import com.api.shops.request.product.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    Product addProduct(AddProductRequest request);
    Product updateProduct(Long productId, UpdateProductRequest request);
    void deleteProduct(Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
