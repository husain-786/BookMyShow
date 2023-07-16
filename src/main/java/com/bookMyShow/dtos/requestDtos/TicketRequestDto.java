package com.bookMyShow.dtos.requestDtos;

import lombok.Data;

import java.util.List;

@Data
public class TicketRequestDto {
    private Integer showId;

    private Integer userId;

    private Boolean havaMeal;

    private Integer mealPrice;

    private List<String> requestedSeats;
}
