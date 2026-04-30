package com.edufood.edufood.repository;

import com.edufood.edufood.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Page<Cafe> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
