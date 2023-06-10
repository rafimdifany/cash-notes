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
public class DetailError {
    private String code;
    private String objectName;
    private String defaultMessage;
    private String field;
    private Object rejectedValue;
    private List<AdditionalData> additionalData;
}
