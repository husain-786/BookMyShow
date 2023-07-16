package com.bookMyShow.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto {
    private LocalTime showTime;

    private Date showDate;

    private String location;

    private String movieName;

    private String theaterName;

    private String bookedSeats;

    private Integer totalPrice;

    private ResultDto resultDto;

    @Override
    public String toString() {
        return "TicketDetails:-" +
                "\n\tShowTime = " + showTime +
                "\n\tShowDate = " + showDate +
                "\n\tLocation = " + location +
                "\n\tMovieName = " + movieName +
                "\n\tTheaterName = " + theaterName +
                "\n\tBookedSeats = " + bookedSeats +
                "\n\tTotalPrice = " + totalPrice;
    }
}
