package com.edufood.edufood.controller;

import com.edufood.edufood.entity.Dish;
import com.edufood.edufood.service.CartService;
import com.edufood.edufood.service.DishService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final DishService dishService;

    @PostMapping("/cart/add/{dishId}")
    public String addToCart(@PathVariable Long dishId,
                            @RequestParam(defaultValue = "1") int quantity,
                            @RequestParam(defaultValue = "/cafes") String redirect,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        Cookie cookie = cartService.addToCart(request, dishId, quantity);
        response.addCookie(cookie);
        return "redirect:" + redirect;
    }

    @PostMapping("/cart/remove/{dishId}")
    public String removeFromCart(@PathVariable Long dishId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Cookie cookie = cartService.removeFromCart(request, dishId);
        response.addCookie(cookie);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartPage(HttpServletRequest request,
                           Authentication authentication,
                           Model model) {
        Map<Long, Integer> cart = cartService.getCart(request);


        List<Dish> dishes = dishService.getByIds(new ArrayList<>(cart.keySet()));

        List<Map<String, Object>> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Dish dish : dishes) {
            int qty = cart.get(dish.getId());
            BigDecimal subtotal = dish.getPrice().multiply(BigDecimal.valueOf(qty));
            total = total.add(subtotal);

            Map<String, Object> item = new HashMap<>();
            item.put("dish", dish);
            item.put("quantity", qty);
            item.put("subtotal", subtotal);
            items.add(item);
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("cartCount", cartService.getTotalCount(request));
        model.addAttribute("username",
                authentication != null ? authentication.getName() : null);
        return "cart/cart";
    }
}