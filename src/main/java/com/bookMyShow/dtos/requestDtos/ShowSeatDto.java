package com.bookMyShow.dtos.requestDtos;

import lombok.Data;

@Data
public class ShowSeatDto {
    private Integer showId;
    private Integer priceForClassicSeats;
    private Integer priceForPremiumSeats;
}
