package com.bookMyShow.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDto {
    private String statusCode;
    private String statusMessage;
    private String statusMessageDescription;
}
