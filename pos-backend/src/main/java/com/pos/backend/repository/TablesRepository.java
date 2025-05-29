package com.pos.backend.repository;


import com.pos.backend.model.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Long> {
    boolean existsByName(String name);

    List<com.pos.backend.model.Tables> findByStatus(Tables.TableStatus status);

    List<Tables> findByCapacityGreaterThanEqual(Integer capacity);

    List<Tables> findByStatusAndCapacityGreaterThanEqual(Tables.TableStatus status, Integer capacity);
}
