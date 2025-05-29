/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.Impl;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.customer.CustomerRequest;
import com.pos.backend.dto.customer.CustomerResponse;
import com.pos.backend.model.Customer;
import com.pos.backend.repository.CustomerRepository;
import com.pos.backend.service.base.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private CustomerResponse mapToResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    private Customer mapToEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        return customer;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy khách hàng với ID: " + id));
        return mapToResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        // Kiểm tra số điện thoại hoặc email trùng lặp (ví dụ: là unique)
        if (customerRepository.findByPhone(customerRequest.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại: " + customerRequest.getPhone());
        }
        if (customerRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email đã tồn tại: " + customerRequest.getEmail());
        }

        Customer customer = mapToEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return mapToResponse(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy khách hàng để cập nhật với ID: " + id));

        // Kiểm tra trùng lặp số điện thoại nếu thay đổi
        if (!existingCustomer.getPhone().equals(customerRequest.getPhone())) {
            if (customerRepository.findByPhone(customerRequest.getPhone()).isPresent()) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại: " + customerRequest.getPhone());
            }
        }

        // Kiểm tra trùng lặp email nếu thay đổi
        if (!existingCustomer.getEmail().equals(customerRequest.getEmail())) {
            if (customerRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email đã tồn tại: " + customerRequest.getEmail());
            }
        }

        existingCustomer.setName(customerRequest.getName());
        existingCustomer.setPhone(customerRequest.getPhone());
        existingCustomer.setEmail(customerRequest.getEmail());
        existingCustomer.setAddress(customerRequest.getAddress());
        // loyaltyPoints thường không được cập nhật qua request này, mà qua các giao dịch

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return mapToResponse(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy khách hàng để xóa với ID: " + id);
        }
        customerRepository.deleteById(id);
    }
}
