package com.edufood.edufood.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;
}
