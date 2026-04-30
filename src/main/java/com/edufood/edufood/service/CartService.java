package com.edufood.edufood.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartService {

    private static final String COOKIE_NAME = "cart";

    public Map<Long, Integer> getCart(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return new LinkedHashMap<>();

        return Arrays.stream(cookies)
                .filter(c -> COOKIE_NAME.equals(c.getName()))
                .findFirst()
                .map(c -> parseCart(c.getValue()))
                .orElse(new LinkedHashMap<>());
    }

    public Cookie addToCart(HttpServletRequest request, Long dishId, int quantity) {
        Map<Long, Integer> cart = getCart(request);
        cart.merge(dishId, quantity, Integer::sum);
        log.debug("Корзина после добавления: {}", cart);
        log.debug("Добавлено в корзину: dishId={}, qty={}", dishId, quantity);
        return buildCookie(cart);
    }

    public Cookie removeFromCart(HttpServletRequest request, Long dishId) {
        Map<Long, Integer> cart = getCart(request);
        cart.remove(dishId);
        log.debug("Удалено из корзины: dishId={}", dishId);
        return buildCookie(cart);
    }

    public Cookie clearCart() {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

    public int getTotalCount(HttpServletRequest request) {
        return getCart(request).values().stream()
                .mapToInt(Integer::intValue).sum();
    }

    private Map<Long, Integer> parseCart(String value) {
        Map<Long, Integer> cart = new LinkedHashMap<>();
        if (value == null || value.isBlank()) return cart;

        for (String entry : value.split("\\|")) {
            String[] parts = entry.split(":");
            if (parts.length == 2) {
                try {
                    cart.put(Long.parseLong(parts[0]), Integer.parseInt(parts[1]));
                } catch (NumberFormatException e) {
                    log.warn("Некорректная запись в корзине: {}", entry);
                }
            }
        }
        return cart;
    }

    private Cookie buildCookie(Map<Long, Integer> cart) {
        String value = cart.entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue())
                .collect(Collectors.joining("|"));

        Cookie cookie = new Cookie(COOKIE_NAME, value);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        return cookie;
    }
}