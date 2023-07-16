package com.bookMyShow.services;

import com.bookMyShow.dtos.requestDtos.TheaterEntryDto;
import com.bookMyShow.dtos.requestDtos.TheaterSeatsEntryDto;
import com.bookMyShow.dtos.responseDtos.TheaterResponseDto;
import com.bookMyShow.entities.Theater;
import com.bookMyShow.entities.TheaterSeat;
import com.bookMyShow.enums.SeatType;
import com.bookMyShow.exceptions.TheaterNotFoundException;
import com.bookMyShow.repositories.TheaterRepository;
import com.bookMyShow.transformers.TheaterTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    public String addTheater(TheaterEntryDto theaterEntryDto) {
        // we have data into dto to save to database, but only entity can be saved to database
        // so converting theaterEntryDto into Theater Entity........
        Theater theater = TheaterTransformer.convertDtoToEntity(theaterEntryDto);
        theaterRepository.save(theater);

        return "Theater saved successfully";
    }

    public TheaterResponseDto getTheaterByLocation(String location) throws TheaterNotFoundException {
        Theater theater = theaterRepository.findByLocation(location);

        if (theater == null){
            throw new TheaterNotFoundException("Theater is not present at location:- "+location);
        }

        // We cannot send the entity as it is to the client, so need to convert the Entity into Dto t send only required data....
        TheaterResponseDto theaterResponseDto = TheaterTransformer.convertEntityToDto(theater);

        return theaterResponseDto;
    }

    public String addTheaterSeats(TheaterSeatsEntryDto theaterSeatsEntryDto) throws TheaterNotFoundException {
        Theater theater = theaterRepository.findByLocation(theaterSeatsEntryDto.getLocation());
        // If theater is not present at requested location, throw custom exception.......
        if (theater == null){
            throw new TheaterNotFoundException("No Theater Found at location:- "+theaterSeatsEntryDto.getLocation());
        }

        int columns = theaterSeatsEntryDto.getNoOfSeatsInSingleRow();
        int noOfClassicSeats = theaterSeatsEntryDto.getNoOfClassicSeats();
        int noOfPremiumSeats = theaterSeatsEntryDto.getNoOfPremiumSeats();

        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        // Adding Seats in the Theater..........
        int counter = 1;
        char ch = 'A';
        // Adding Classic Seats in the theater......
        for (int count=1; count<=noOfClassicSeats; count++) {
            String seatNo = counter+ "" + ch + "";
            ch++;
            if (ch - 'A' == columns) {
                counter++;
                ch = 'A';
            }
            // Unidirectional Mapping is done here.......
            // mapping parent entity with the child entity.......
            TheaterSeat theaterSeat = new TheaterSeat();
            theaterSeat.setSeatNo(seatNo);
            theaterSeat.setSeatType(SeatType.CLASSIC);
            theaterSeat.setTheater(theater);

            // Bidirectional Mapping is done here.......
            // mapping child entity with parent entity......
            theaterSeatList.add(theaterSeat);
        }
        // Adding Premium Seats in the theater......
        counter = (int) Math.ceil((double) noOfClassicSeats/columns) + 1;
        ch='A';
        for (int count=1; count<=noOfPremiumSeats; count++){
            String seatNo = counter+ "" + ch + "";
            ch++;
            if (ch - 'A' == columns) {
                counter++;
                ch = 'A';
            }
            // Unidirectional Mapping is done here.......
            // mapping parent entity with the child entity.......
            TheaterSeat theaterSeat = new TheaterSeat();
            theaterSeat.setSeatNo(seatNo);
            theaterSeat.setSeatType(SeatType.PREMIUM);
            theaterSeat.setTheater(theater);

            // Bidirectional Mapping is done here.......
            // mapping child entity with parent entity......
            theaterSeatList.add(theaterSeat);
        }

        theaterRepository.save(theater);

        return "Theater Seats Added Successfully";
    }
}
