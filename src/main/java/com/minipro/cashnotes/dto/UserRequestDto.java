package com.minipro.cashnotes.dto;

import com.minipro.cashnotes.validator.constraint.UsernameUniqueConstraint;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @UsernameUniqueConstraint
    private String username;

    private String password;

    private String email;

    private Boolean isVerified = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

