package com.edufood.edufood.repository;

import com.edufood.edufood.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Page<Dish> findByCafeId(Long cafeId, Pageable pageable);
}
