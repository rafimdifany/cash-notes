package com.minipro.cashnotes.dto.advice;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultAttributeError {

    private int status;
    private String message;
    private String error;
    private String path;
    private String timestamp;
    private List<DetailError> details;

}
