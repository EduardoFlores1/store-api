package com.api.shops.service.product;

import com.api.shops.exceptions.CategoryNotFoundException;
import com.api.shops.exceptions.ProductNotFoundException;
import com.api.shops.mappers.ProductMapper;
import com.api.shops.model.Category;
import com.api.shops.model.Product;
import com.api.shops.repository.CategoryRepository;
import com.api.shops.repository.ProductRepository;
import com.api.shops.request.product.AddProductRequest;
import com.api.shops.request.product.UpdateProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    @Transactional
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(productMapper.addToModel(request));
    }

    @Override
    public Product updateProduct(Long productId, UpdateProductRequest request) {
        return productRepository.findById(productId)
                .map(existProduct -> {
                    Category category = Optional.ofNullable(categoryRepository.findByName(request.getName()))
                            .orElseThrow(() -> new CategoryNotFoundException("Category Not Found!"));

                    existProduct.setName(request.getName());
                    existProduct.setBrand(request.getBrand());
                    existProduct.setPrice(request.getPrice());
                    existProduct.setInventory(request.getInventory());
                    existProduct.setDescription(request.getDescription());
                    existProduct.setCategory(category);
                    return productRepository.save(existProduct);
                })
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(
                productRepository::delete,
                () -> {throw new ProductNotFoundException("Product Not Found!");}
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
