package com.edufood.edufood.service;

import com.edufood.edufood.entity.Dish;
import com.edufood.edufood.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DishService {

    private final DishRepository dishRepository;

    public Page<Dish> getDishesByCafe(Long cafeId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return dishRepository.findByCafeIdWithCafe(cafeId, pageable);
    }

    public Dish getById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Блюдо не найдено: {}", id);
                    return new RuntimeException("Блюдо не найдено: " + id);
                });
    }

    public List<Dish> getByIds(List<Long> ids) {
        return dishRepository.findAllWithCafeByIds(ids);
    }
}
