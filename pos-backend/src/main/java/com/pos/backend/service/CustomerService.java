/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service;

import com.pos.backend.dto.customer.CustomerRequest;
import com.pos.backend.dto.customer.CustomerResponse;

import java.util.List;

/**
 * @author 04dkh
 */
public interface CustomerService {

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long id);

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest);

    void deleteCustomer(Long id);
}
