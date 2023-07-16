package com.bookMyShow.controllers;

import com.bookMyShow.dtos.requestDtos.AddShowDto;
import com.bookMyShow.dtos.requestDtos.ShowSeatDto;
import com.bookMyShow.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping("/addShow")
    public ResponseEntity<String> addShow(@RequestBody AddShowDto addShowDto){
        try{
            String response = showService.addShow(addShowDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/associateShowSeats")
    public ResponseEntity<String> associateShowSeats(@RequestBody ShowSeatDto showSeatDto){
        try {
            String response = showService.associateShowSeats(showSeatDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CREATED);
        }
    }

    @GetMapping("/getMovieHavingMaxShowsOnGivenDate")
    public ResponseEntity<String> getMovieHavingMaxShowsOnGivenDate(@RequestParam String dateString){
        try{
            String response = showService.getMovieHavingMaxShowsOnGivenDate(dateString);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
