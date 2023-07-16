package com.bookMyShow.dtos.requestDtos;

import lombok.Data;

@Data
public class TheaterSeatsEntryDto {
    private Integer noOfSeatsInSingleRow;
    private Integer noOfClassicSeats;
    private Integer noOfPremiumSeats;
    private String location;
}
