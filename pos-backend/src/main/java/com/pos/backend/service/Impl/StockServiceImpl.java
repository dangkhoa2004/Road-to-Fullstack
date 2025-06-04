package com.pos.backend.service.Impl;

import com.pos.backend.dto.stock_in.StockInRequest;
import com.pos.backend.dto.stock_in.StockInResponse;
import com.pos.backend.dto.stock_out.StockOutRequest;
import com.pos.backend.dto.stock_out.StockOutResponse;
import com.pos.backend.model.Employee;
import com.pos.backend.model.Product;
import com.pos.backend.model.StockIn;
import com.pos.backend.model.StockOut;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.repository.ProductRepository;
import com.pos.backend.repository.StockInRepository;
import com.pos.backend.repository.StockOutRepository;
import com.pos.backend.service.base.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockInRepository stockInRepository;
    private final StockOutRepository stockOutRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public StockInResponse createStockIn(StockInRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow();
        Employee employee = request.getEmployeeId() != null
                ? employeeRepository.findById(request.getEmployeeId()).orElse(null)
                : null;

        StockIn stockIn = new StockIn();
        stockIn.setProduct(product);
        stockIn.setQuantity(request.getQuantity());
        stockIn.setNote(request.getNote());
        stockIn.setEmployee(employee);
        stockInRepository.save(stockIn);

        return mapToStockInResponse(stockIn);
    }

    @Override
    @Transactional
    public StockOutResponse createStockOut(StockOutRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow();
        Employee employee = request.getEmployeeId() != null
                ? employeeRepository.findById(request.getEmployeeId()).orElse(null)
                : null;

        StockOut stockOut = new StockOut();
        stockOut.setProduct(product);
        stockOut.setQuantity(request.getQuantity());
        stockOut.setNote(request.getNote());
        stockOut.setEmployee(employee);
        stockOutRepository.save(stockOut);

        return mapToStockOutResponse(stockOut);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockInResponse> getAllStockIn() {
        return stockInRepository.findAll().stream().map(this::mapToStockInResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockOutResponse> getAllStockOut() {
        return stockOutRepository.findAll().stream().map(this::mapToStockOutResponse).collect(Collectors.toList());
    }

    private StockInResponse mapToStockInResponse(StockIn s) {
        return StockInResponse.builder()
                .id(s.getId())
                .productId(s.getProduct().getId())
                .productName(s.getProduct().getName())
                .quantity(s.getQuantity())
                .note(s.getNote())
                .employeeId(s.getEmployee() != null ? s.getEmployee().getId() : null)
                .employeeName(s.getEmployee() != null ? s.getEmployee().getName() : null)
                .build();
    }

    private StockOutResponse mapToStockOutResponse(StockOut s) {
        return StockOutResponse.builder()
                .id(s.getId())
                .productId(s.getProduct().getId())
                .productName(s.getProduct().getName())
                .quantity(s.getQuantity())
                .note(s.getNote())
                .employeeId(s.getEmployee() != null ? s.getEmployee().getId() : null)
                .employeeName(s.getEmployee() != null ? s.getEmployee().getName() : null)
                .build();
    }
}
