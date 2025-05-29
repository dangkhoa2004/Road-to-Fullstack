/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.base;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.invoice.InvoiceResponse;
import com.pos.backend.model.Invoice;

import java.util.List;

public interface InvoiceService {

    List<InvoiceResponse> getAllInvoices();

    InvoiceResponse create(com.pos.backend.dto.invoice.InvoiceRequest request);

    InvoiceResponse getById(Long id);

    List<InvoiceResponse> getByStatus(Invoice.InvoiceStatus status);

    InvoiceResponse updateStatus(Long id, Invoice.InvoiceStatus status);

    void delete(Long id);
}
