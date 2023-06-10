package com.minipro.cashnotes.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BalanceType balanceType;

    private BigDecimal amount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne()
    private Users users;


    public enum BalanceType {
        CASH,
        GOPAY,
        ASTRAPAY,
        MANDIRI,
        BCA,
        DANA
    }

}
