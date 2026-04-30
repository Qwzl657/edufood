package com.edufood.edufood.repository;

import com.edufood.edufood.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("SELECT d FROM Dish d JOIN FETCH d.cafe WHERE d.cafe.id = :cafeId")
    Page<Dish> findByCafeIdWithCafe(@Param("cafeId") Long cafeId, Pageable pageable);

    @Query("SELECT d FROM Dish d JOIN FETCH d.cafe WHERE d.id IN :ids")
    List<Dish> findAllWithCafeByIds(@Param("ids") List<Long> ids);
}
