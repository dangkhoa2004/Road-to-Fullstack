/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.repository;

import com.pos.backend.model.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 04dkh
 */
@Repository
public interface DiscountTypeRepository extends JpaRepository<DiscountType, Long>{

}
