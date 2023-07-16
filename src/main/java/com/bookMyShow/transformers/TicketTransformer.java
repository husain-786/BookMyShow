package com.bookMyShow.transformers;

import com.bookMyShow.dtos.requestDtos.TicketRequestDto;
import com.bookMyShow.dtos.responseDtos.TicketResponseDto;
import com.bookMyShow.entities.Show;
import com.bookMyShow.entities.Ticket;

import java.util.List;

public class TicketTransformer {
    public static String convertRequestedSeatsToString(List<String> requestedSeats){
        String seats = " ";
        for (String seat: requestedSeats){
            seats += seat + " ";
        }
        return seats;
    }

    public static TicketResponseDto convertEntityToDto(Show show, Ticket ticket){
        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .showDate(show.getDate())
                .showTime(show.getTime())
                .movieName(show.getMovie().getName())
                .location(show.getTheater().getLocation())
                .theaterName(show.getTheater().getName())
                .bookedSeats(ticket.getBookedSeats())
                .totalPrice(ticket.getTotalTicketPrice())
                .build();

        return ticketResponseDto;
    }
}
