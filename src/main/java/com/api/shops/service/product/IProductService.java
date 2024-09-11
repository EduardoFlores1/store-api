package com.api.shops.service.product;

import com.api.shops.model.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    Product addProduct(Product product);
    void updateProduct(Long productId, Product product);
    void deleteProduct(Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
