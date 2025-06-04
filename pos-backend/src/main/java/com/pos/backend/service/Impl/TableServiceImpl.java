package com.pos.backend.service.Impl;

import com.pos.backend.dto.tables.TableRequest;
import com.pos.backend.dto.tables.TableResponse;
import com.pos.backend.model.Tables;
import com.pos.backend.repository.TablesRepository;
import com.pos.backend.service.base.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TablesRepository tablesRepository;

    /* ------------------------- CREATE ------------------------- */
    @Override
    @Transactional
    public TableResponse create(TableRequest request) {
        if (tablesRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Table name already exists");
        }
        Tables table = new Tables();
        table.setName(request.getName());
        if (request.getStatus() != null) {
            table.setStatus(Tables.TableStatus.valueOf(request.getStatus()));
        } // nếu null giữ mặc định AVAILABLE
        table.setCapacity(request.getCapacity());
        tablesRepository.save(table);
        return mapToResponse(table);
    }

    /* ------------------------- READ ------------------------- */
    @Override
    @Transactional(readOnly = true)
    public TableResponse getById(Long id) {
        return mapToResponse(tablesRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableResponse> getAll() {
        return tablesRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableResponse> getByStatus(Tables.TableStatus status) {
        return tablesRepository.findByStatus(status).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /* ------------------------- UPDATE ------------------------- */
    @Override
    @Transactional
    public TableResponse update(Long id, TableRequest request) {
        Tables table = tablesRepository.findById(id).orElseThrow();
        if (request.getName() != null && !request.getName().equalsIgnoreCase(table.getName())) {
            if (tablesRepository.existsByName(request.getName())) {
                throw new IllegalArgumentException("Table name already exists");
            }
            table.setName(request.getName());
        }
        if (request.getStatus() != null) {
            table.setStatus(Tables.TableStatus.valueOf(request.getStatus()));
        }
        if (request.getCapacity() != null) {
            table.setCapacity(request.getCapacity());
        }
        return mapToResponse(table);
    }

    @Override
    @Transactional
    public TableResponse updateStatus(Long id, Tables.TableStatus status) {
        Tables table = tablesRepository.findById(id).orElseThrow();
        table.setStatus(status);
        return mapToResponse(table);
    }

    /* ------------------------- DELETE ------------------------- */
    @Override
    @Transactional
    public void delete(Long id) {
        tablesRepository.deleteById(id);
    }

    /* ------------------------- MAPPER ------------------------- */
    private TableResponse mapToResponse(Tables t) {
        return TableResponse.builder()
                .id(t.getId())
                .name(t.getName())
                .status(t.getStatus().name())
                .capacity(t.getCapacity())
                .build();
    }
}
