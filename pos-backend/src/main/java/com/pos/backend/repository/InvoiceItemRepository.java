/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pos.backend.model.InvoiceItem;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

    // Tìm items theo invoice
    List<InvoiceItem> findByInvoiceId(Long invoiceId);

    // Tìm items theo product
    List<InvoiceItem> findByProductId(Long productId);

    // Tìm items theo invoice và product
    List<InvoiceItem> findByInvoiceIdAndProductId(Long invoiceId, Long productId);

    // Thống kê sản phẩm bán chạy
    @Query("SELECT ii.product.name, SUM(ii.quantity) as totalQuantity " +
            "FROM InvoiceItem ii JOIN ii.invoice i " +
            "WHERE i.status = 'completed' " +
            "GROUP BY ii.product.id, ii.product.name " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> getTopSellingProducts();

    // Xóa tất cả items của một invoice
    void deleteByInvoiceId(Long invoiceId);
}
