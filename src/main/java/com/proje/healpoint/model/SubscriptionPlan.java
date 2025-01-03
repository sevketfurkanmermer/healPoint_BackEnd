package com.proje.healpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {

    //ADMİN OLUŞTURUR

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer durationInMonths;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public SubscriptionPlan(String name, BigDecimal price, Integer durationInMonths) {
        this.name = name;
        this.price = price;
        this.durationInMonths = durationInMonths;
    }
}
