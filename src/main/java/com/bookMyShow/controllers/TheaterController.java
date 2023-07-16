package com.bookMyShow.controllers;

import com.bookMyShow.dtos.requestDtos.TheaterEntryDto;
import com.bookMyShow.dtos.requestDtos.TheaterSeatsEntryDto;
import com.bookMyShow.dtos.responseDtos.ResultDto;
import com.bookMyShow.dtos.responseDtos.TheaterResponseDto;
import com.bookMyShow.entities.Theater;
import com.bookMyShow.services.TheaterService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theater")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    @PostMapping("/addTheater")
    public ResponseEntity<String> addTheater(@RequestBody TheaterEntryDto theaterEntryDto){
        try {
            String response = theaterService.addTheater(theaterEntryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTheaterByLocation/{location}")
    public ResponseEntity<TheaterResponseDto> getTheaterByLocation(@PathVariable String location){
        try{
            TheaterResponseDto theaterResponseDto = theaterService.getTheaterByLocation(location);

            // setting the status result.....
            ResultDto resultDto = new ResultDto("200", "SUCCESSFULL", "Theater added succesfully");
            theaterResponseDto.setResultDto(resultDto);

            return new ResponseEntity<>(theaterResponseDto, HttpStatus.OK);
        }
        catch (Exception e){
            TheaterResponseDto theaterResponseDto = new TheaterResponseDto();

            // setting the status result.....
            ResultDto resultDto = new ResultDto("500", "FAILURE", "Something Went Wrong");
            theaterResponseDto.setResultDto(resultDto);

            return new ResponseEntity<>(theaterResponseDto, HttpStatus.NOT_FOUND);
        }
    }

    // Adding or say setting the theater seats for a theater......
    @PostMapping("/addTheaterSeats")
    public ResponseEntity<String> addTheaterSeats(@RequestBody TheaterSeatsEntryDto theaterSeatsEntryDto){
        try{
            String response = theaterService.addTheaterSeats(theaterSeatsEntryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
