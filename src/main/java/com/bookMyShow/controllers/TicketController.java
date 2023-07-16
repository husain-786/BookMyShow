package com.bookMyShow.controllers;

import com.bookMyShow.dtos.requestDtos.TicketRequestDto;
import com.bookMyShow.dtos.responseDtos.ResultDto;
import com.bookMyShow.dtos.responseDtos.TicketResponseDto;
import com.bookMyShow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/bookTicket")
    public ResponseEntity<TicketResponseDto> bookTicket(@RequestBody TicketRequestDto ticketRequestDto){
        try{
            TicketResponseDto ticketResponseDto = ticketService.bookTicket(ticketRequestDto);
            // Adding response status details.....
            ticketResponseDto.setResultDto(new ResultDto("201", "SUCCESS", "Ticket created and saved Successfully"));
            return new ResponseEntity<>(ticketResponseDto, HttpStatus.CREATED);
        }
        catch (Exception e){
            TicketResponseDto ticketResponseDto = new TicketResponseDto();
            ticketResponseDto.setResultDto(new ResultDto("500", "FAILURE", "Tickets are Not Available"));
            return new ResponseEntity<>(ticketResponseDto, HttpStatus.NOT_FOUND);
        }
    }
}
