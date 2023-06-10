package com.minipro.cashnotes.dto;

import com.minipro.cashnotes.entity.UserBalance.BalanceType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceDto {
    private Long id;

    private BalanceType balanceType;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDto user;
}
