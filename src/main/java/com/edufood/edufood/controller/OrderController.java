package com.edufood.edufood.controller;

import com.edufood.edufood.entity.Order;
import com.edufood.edufood.service.CartService;
import com.edufood.edufood.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping("/orders/place")
    public String placeOrder(Authentication authentication,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            orderService.placeOrder(authentication.getName(), request);
            response.addCookie(cartService.clearCart());
            return "redirect:/profile";
        } catch (RuntimeException e) {
            log.error("Ошибка оформления заказа: {}", e.getMessage());
            return "redirect:/cart?error=true";
        }
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication,
                          Model model,
                          HttpServletRequest request) {
        List<Order> orders = orderService.getUserOrders(authentication.getName());
        model.addAttribute("orders", orders);
        model.addAttribute("cartCount", cartService.getTotalCount(request));
        model.addAttribute("username", authentication.getName());
        return "user/profile";
    }
}
