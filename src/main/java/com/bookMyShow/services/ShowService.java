package com.bookMyShow.services;

import com.bookMyShow.dtos.requestDtos.AddShowDto;
import com.bookMyShow.dtos.requestDtos.ShowSeatDto;
import com.bookMyShow.entities.*;
import com.bookMyShow.enums.SeatType;
import com.bookMyShow.exceptions.MovieNotFoundException;
import com.bookMyShow.exceptions.ShowNotFoundException;
import com.bookMyShow.exceptions.TheaterNotFoundException;
import com.bookMyShow.repositories.MovieRepository;
import com.bookMyShow.repositories.ShowRepository;
import com.bookMyShow.repositories.TheaterRepository;
import com.bookMyShow.transformers.ShowTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private MovieRepository movieRepository;

    public String addShow(AddShowDto addShowDto) throws TheaterNotFoundException, MovieNotFoundException {
        // Checking whether the Theater is present or not........
        Optional<Theater> optionalTheater = theaterRepository.findById(addShowDto.getTheaterId());
        if (optionalTheater.isEmpty()){
            throw new TheaterNotFoundException("Theater Not Found");
        }
        Theater theater = optionalTheater.get();

        // Checking whether the movie is present or not.........
        Optional<Movie> optionalMovie = movieRepository.findById(addShowDto.getMovieId());
        if (optionalMovie.isEmpty()){
            throw new MovieNotFoundException("Movie Not Found");
        }
        Movie movie = optionalMovie.get();

        // Creating show entity by using addShowDto with the help of ShowTransformer......
        Show show = ShowTransformer.convertDtoToEntity(addShowDto);

        // setting the foreign key for show......
        // Mapping(Unidirectional) Child entity with the parents entity.....
        show.setMovie(movie);
        show.setTheater(theater);
        // As show is being added for the 1st time if we don't save the show first then show will be created twice..
        // first when movie is saved and second when theater is saved, because each time the movie will create show as new
        // and theater will also create it as new...
        show = showRepository.save(show);

        // Bi-directional Mapping:- Mapping Parents Entities(Movie, Theater) with the Show Entity.......
        movie.getShowList().add(show);
        theater.getShowList().add(show);

        // saving the parents so all the changes will be done automatically in the parent and the child entity......
        movieRepository.save(movie);
        theaterRepository.save(theater);

        return "Show for theater (" + theater.getName() + ") at location " + theater.getLocation() + " have been saved Successfully and the showId is:- " + show.getId();
    }

    // this api is basically written to show the ShowSeat details virtually.....
    // while booking the ttket online we see the sit arrangements price and type of seat, that is what we are doing here....
    public String associateShowSeats(ShowSeatDto showSeatDto) throws ShowNotFoundException {
        // Checcking whether the Show is present or not......
        Optional<Show> optionalShow = showRepository.findById(showSeatDto.getShowId());
        if (optionalShow.isEmpty()){
            throw new ShowNotFoundException("Show Not Found");
        }
        Show show = optionalShow.get();

        // getting theater from the show........
        Theater theater = show.getTheater();

        // getting theaterSeat list to find the seatType and seatNo.... to create showSeat........
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        // Getting showSeatList from seat for bidirectional mapping, so that we can keep track of showSeat.....
        // Bidirectional benefit is that, let suppose if a show is deleted then all showSeats or tickets associatd to it must be deleted automatically...
        List<ShowSeat> showSeatList = show.getShowSeatList();

        for (TheaterSeat theaterSeat: theaterSeatList){
            ShowSeat showSeat = new ShowSeat();

            // setting showSeat attributes
            showSeat.setSeatNo(theaterSeat.getSeatNo());
            showSeat.setSeatType(theaterSeat.getSeatType());
            if (showSeat.getSeatType().equals(SeatType.CLASSIC)){
                showSeat.setPrice(showSeatDto.getPriceForClassicSeats());
            }
            else{
                showSeat.setPrice(showSeatDto.getPriceForPremiumSeats());
            }
            showSeat.setAvailable(true);
            showSeat.setFoodAttached(false);

            // Mapping child entity (theater seat) with parent entity(Show).......
            // foreignKey Mapping......
            showSeat.setShow(show);

            // BidirectionalMapping:- Mapping parentEntity(Show) with child entity(theaterSeat)......
            showSeatList.add(showSeat);
        }

        showRepository.save(show);

        return "Show Seats has been Successfully Added";
    }

    public String getMovieHavingMaxShowsOnGivenDate(String dateString) throws ShowNotFoundException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);

        Integer id = showRepository.getMovieHavingMaxShowsOnGivenDate(date);
        if (id == null){
            throw new ShowNotFoundException("No Show Found for the Date:- " + date);
        }
        return showRepository.findById(id).get().getMovie().getName();
    }
}
