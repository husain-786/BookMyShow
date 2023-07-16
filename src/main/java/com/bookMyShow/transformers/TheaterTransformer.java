package com.bookMyShow.transformers;

import com.bookMyShow.dtos.requestDtos.TheaterEntryDto;
import com.bookMyShow.dtos.responseDtos.TheaterResponseDto;
import com.bookMyShow.entities.Theater;

public class TheaterTransformer {
    public static Theater convertDtoToEntity(TheaterEntryDto theaterEntryDto){
        Theater theater = Theater.builder()
                .name(theaterEntryDto.getName())
                .location(theaterEntryDto.getLocation())
                .build();

        return theater;
    }

    public static TheaterResponseDto convertEntityToDto(Theater theater){
        TheaterResponseDto theaterResponseDto = TheaterResponseDto.builder()
                .name(theater.getName())
                .location(theater.getLocation())
                .build();

        return theaterResponseDto;
    }
}
