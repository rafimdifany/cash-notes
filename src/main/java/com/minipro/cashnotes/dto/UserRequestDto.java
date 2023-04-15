package com.minipro.cashnotes.dto;

import com.minipro.cashnotes.validator.constraint.UsernameIsUniqueValidation;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    //TODO: Validator belum jalan
    @UsernameIsUniqueValidation
    private String username;

    private String password;

    private String email;

    private Boolean isVerified = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

