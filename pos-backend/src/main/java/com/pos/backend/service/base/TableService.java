/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.base;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.tables.TableRequest;
import com.pos.backend.dto.tables.TableResponse;
import com.pos.backend.model.Tables;

import java.util.List;

public interface TableService {
    TableResponse create(TableRequest request);

    TableResponse getById(Long id);

    List<TableResponse> getAll();

    List<TableResponse> getByStatus(Tables.TableStatus status);

    TableResponse update(Long id, TableRequest request);

    TableResponse updateStatus(Long id, Tables.TableStatus status); // ✅ đã khớp

    void delete(Long id);
}
