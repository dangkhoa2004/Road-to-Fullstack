/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.repository;

import com.pos.backend.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // Tìm invoice theo customer
    List<Invoice> findByCustomerId(Long customerId);

    // Tìm invoice theo employee
    List<Invoice> findByEmployeeId(Long employeeId);

    // Tìm invoice theo status
    List<Invoice> findByStatus(Invoice.InvoiceStatus status);

    // Tìm invoice theo table
    List<Invoice> findByTableId(Long tableId);

    // Tìm invoice trong khoảng thời gian
    @Query("SELECT i FROM Invoice i WHERE i.createdAt BETWEEN :startDate AND :endDate")
    List<Invoice> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);

    // Tìm invoice với tổng tiền lớn hơn
    List<Invoice> findByTotalAmountGreaterThan(BigDecimal amount);

    // Tìm invoice với phân trang
    Page<Invoice> findByStatus(Invoice.InvoiceStatus status, Pageable pageable);

    // Tìm invoice kèm items
    @Query("SELECT DISTINCT i FROM Invoice i LEFT JOIN FETCH i.invoiceItems WHERE i.id = :id")
    Optional<Invoice> findByIdWithItems(@Param("id") Long id);

    // Tìm invoice kèm payments
    @Query("SELECT DISTINCT i FROM Invoice i LEFT JOIN FETCH i.payments WHERE i.id = :id")
    Optional<com.pos.backend.model.Invoice> findByIdWithPayments(@Param("id") Long id);

    // Thống kê doanh thu theo tháng
    @Query("SELECT MONTH(i.createdAt) as month, SUM(i.totalAmount) as revenue " +
            "FROM Invoice i WHERE YEAR(i.createdAt) = :year AND i.status = 'completed' " +
            "GROUP BY MONTH(i.createdAt) ORDER BY MONTH(i.createdAt)")
    List<Object[]> getMonthlyRevenue(@Param("year") int year);
}
