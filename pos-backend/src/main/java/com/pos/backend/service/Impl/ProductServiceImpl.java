package com.pos.backend.service.Impl;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author 04dkh
 */

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.backend.dto.product.ProductRequest;
import com.pos.backend.dto.product.ProductResponse;
import com.pos.backend.model.Category;
import com.pos.backend.model.Product;
import com.pos.backend.repository.CategoryRepository;
import com.pos.backend.repository.ProductRepository;
import com.pos.backend.service.base.ProductService;
import com.pos.backend.util.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDto(product);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapRequestToEntity(productRequest, product);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        mapRequestToEntity(productRequest, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    // Helper method to map ProductRequest to Product entity
    private void mapRequestToEntity(ProductRequest request, Product product) {
        product.setBarcode(request.getBarcode());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImagePath(request.getImagePath());
        product.setIsActive(request.getIsActive() != null ? request.getIsActive() : true); // Ensure default if null

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found with id: " + request.getCategoryId()));
            product.setCategory(category);
        } else {
            product.setCategory(null); // Or handle as per business logic, e.g., throw error
        }
    }

    // Helper method to convert Product entity to ProductResponse DTO
    private ProductResponse convertToDto(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setBarcode(product.getBarcode());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setImagePath(product.getImagePath());
        dto.setIsActive(product.getIsActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
}
