package com.edufood.edufood.service;

import com.edufood.edufood.entity.Dish;
import com.edufood.edufood.entity.Order;
import com.edufood.edufood.entity.OrderItem;
import com.edufood.edufood.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishService dishService;
    private final UserService userService;
    private final CartService cartService;

    @Transactional
    public void placeOrder(String email, HttpServletRequest request) {
        log.info("Оформление заказа для: {}", email);

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Map<Long, Integer> cart = cartService.getCart(request);
        if (cart.isEmpty()) throw new RuntimeException("Корзина пуста");

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Dish dish = dishService.getById(entry.getKey());
            int qty = entry.getValue();
            BigDecimal itemPrice = dish.getPrice().multiply(BigDecimal.valueOf(qty));
            total = total.add(itemPrice);

            items.add(OrderItem.builder()
                    .dish(dish)
                    .quantity(qty)
                    .price(dish.getPrice())
                    .build());
        }

        Order order = Order.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .totalPrice(total)
                .items(items)
                .build();

        items.forEach(item -> item.setOrder(order));
        orderRepository.save(order);
        log.info("Заказ оформлен на сумму: {}", total);
    }

    public List<Order> getUserOrders(String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }
}
