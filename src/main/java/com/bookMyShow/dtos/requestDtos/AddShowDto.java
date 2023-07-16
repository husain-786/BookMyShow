package com.bookMyShow.dtos.requestDtos;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class AddShowDto {
    private LocalTime showTime;
    private Date showDate;
    private Integer theaterId;
    private Integer movieId;
}
