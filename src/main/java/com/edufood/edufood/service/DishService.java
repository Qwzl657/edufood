package com.edufood.edufood.service;

import com.edufood.edufood.entity.Dish;
import com.edufood.edufood.entity.Order;
import com.edufood.edufood.entity.OrderItem;
import com.edufood.edufood.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class DishService {

    private final DishRepository dishRepository;

    public Page<Dish> getDishesByCafe(Long cafeId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return dishRepository.findByCafeId(cafeId, pageable);
    }

    public Dish getById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Блюдо не найдено: " + id));
    }
}
