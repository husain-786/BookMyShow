package com.bookMyShow.transformers;

import com.bookMyShow.dtos.requestDtos.MovieEntryDto;
import com.bookMyShow.entities.Movie;
import lombok.Data;

public class MovieTransformer {
    public static Movie convertDtoToEntity(MovieEntryDto movieEntryDto){
        Movie movie = Movie.builder()
                .name(movieEntryDto.getName())
                .genre(movieEntryDto.getGenre())
                .duration(movieEntryDto.getDuration())
                .rating(movieEntryDto.getRating())
                .releaseDate(movieEntryDto.getReleaseDate())
                .language(movieEntryDto.getLanguage())
                .build();

        return movie;
    }
}
