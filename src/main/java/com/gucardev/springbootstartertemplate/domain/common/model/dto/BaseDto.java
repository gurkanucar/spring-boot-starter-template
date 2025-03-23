package com.gucardev.springbootstartertemplate.domain.common.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class BaseDto {

    //    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createdDate;

    //    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;

}

