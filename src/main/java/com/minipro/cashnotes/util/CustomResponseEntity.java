package com.minipro.cashnotes.util;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseEntity<T> {

    private String message;
    private T data;
}
