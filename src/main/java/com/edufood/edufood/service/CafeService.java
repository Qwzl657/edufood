package com.edufood.edufood.service;

import com.edufood.edufood.entity.*;
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
public class CafeService {

    private final CafeRepository cafeRepository;

    public Page<Cafe> getCafes(String search, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (search != null && !search.isBlank()) {
            log.debug("Поиск кафе по имени: {}", search);
            return cafeRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return cafeRepository.findAll(pageable);
    }

    public Cafe getById(Long id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Кафе не найдено: {}", id);
                    return new RuntimeException("Кафе не найдено");
                });
    }
}
