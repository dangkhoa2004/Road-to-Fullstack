/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.common;

/**
 *
 * @author 04dkh
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "status", "string", "data" })
public class ApiResponse<T> {

    @JsonProperty("string")
    private String message;
    private String status;
    private T data;
}
