package com.edufood.edufood.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.util.List;

@Entity
@Table(name = "cafes")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String imageUrl;

    @OneToMany(mappedBy = "cafe", fetch = FetchType.LAZY)
    private List<Dish> dishes;
}
