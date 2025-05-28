/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service;

/**
 * @author 04dkh
 */
import com.pos.backend.dto.category.CategoryRequest;
import com.pos.backend.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);
}
