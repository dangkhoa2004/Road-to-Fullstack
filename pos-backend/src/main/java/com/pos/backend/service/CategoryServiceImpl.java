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
import com.pos.backend.model.Category;
import com.pos.backend.repository.CategoryRepository;
import com.pos.backend.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor Injection để Spring tự động inject CategoryRepository
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Phương thức trợ giúp để chuyển đổi Category Entity sang CategoryResponse
     * DTO. Đây là một best practice để tách biệt logic giữa Entity và DTO.
     */
    private CategoryResponse mapToResponse(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    /**
     * Phương thức trợ giúp để chuyển đổi CategoryRequest DTO sang Category
     * Entity. Thường dùng khi tạo mới.
     */
    private Category mapToEntity(CategoryRequest request) {
        if (request == null) {
            return null;
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        // Lấy tất cả Category từ DB, sau đó chuyển đổi từng Entity sang Response DTO
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse) // Sử dụng phương thức mapToResponse
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        // Tìm Category theo ID, nếu không tìm thấy thì ném ngoại lệ NoSuchElementException
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy danh mục với ID: " + id));
        return mapToResponse(category); // Chuyển đổi sang Response DTO
    }

    @Override
    @Transactional // Đảm bảo thao tác này chạy trong một transaction
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        // Kiểm tra xem tên danh mục đã tồn tại chưa để tránh lỗi UNIQUE constraint và cung cấp thông báo rõ ràng hơn
        if (categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại: " + categoryRequest.getName());
        }

        // Chuyển đổi Request DTO sang Entity
        Category category = mapToEntity(categoryRequest);
        // Lưu Entity vào DB
        Category savedCategory = categoryRepository.save(category);
        // Chuyển đổi Entity đã lưu sang Response DTO để trả về
        return mapToResponse(savedCategory);
    }

    @Override
    @Transactional // Đảm bảo thao tác này chạy trong một transaction
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        // Tìm Category hiện có theo ID, nếu không tìm thấy thì ném ngoại lệ
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy danh mục để cập nhật với ID: " + id));

        // Kiểm tra xem tên danh mục mới có bị trùng với danh mục khác không
        // Chỉ kiểm tra nếu tên được thay đổi và tên mới đã tồn tại
        if (!existingCategory.getName().equals(categoryRequest.getName())
                && categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại: " + categoryRequest.getName());
        }

        // Cập nhật các trường của Entity hiện có
        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());
        // Lưu Entity đã cập nhật vào DB
        Category updatedCategory = categoryRepository.save(existingCategory);
        // Chuyển đổi Entity đã cập nhật sang Response DTO
        return mapToResponse(updatedCategory);
    }

    @Override
    @Transactional // Đảm bảo thao tác này chạy trong một transaction
    public void deleteCategory(Long id) {
        // Kiểm tra xem danh mục có tồn tại trước khi xóa không
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy danh mục để xóa với ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
