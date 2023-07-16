package com.bookMyShow.services;

import com.bookMyShow.dtos.requestDtos.MovieEntryDto;
import com.bookMyShow.entities.Movie;
import com.bookMyShow.repositories.MovieRepository;
import com.bookMyShow.transformers.MovieTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public String addMovie(MovieEntryDto movieEntryDto) {
        // getting entity using dto with the help of transformer.....
        Movie movie = MovieTransformer.convertDtoToEntity(movieEntryDto);

        // saving movie details in the table with help of repository......
        movieRepository.save(movie);

        return "Movie saved successfully";
    }
}
