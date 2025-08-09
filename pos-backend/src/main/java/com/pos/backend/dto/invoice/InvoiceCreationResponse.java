/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.invoice;

/**
 *
 * @author caodangkhoa
 */
import com.pos.backend.dto.invoice.InvoiceResponse;
import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) for the response when a new invoice is created.
 * It contains the full invoice data along with a payment URL if applicable.
 */
@Data
@Builder
public class InvoiceCreationResponse {

    /**
     * The details of the created invoice.
     */
    private InvoiceResponse invoice;

    /**
     * The checkout URL for the payment gateway (e.g., payOS).
     * This will be null if no online payment is required.
     */
    private String paymentUrl;
}
