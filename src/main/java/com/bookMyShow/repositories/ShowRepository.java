package com.bookMyShow.repositories;

import com.bookMyShow.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
    @Query(value = "select movie_id from shows where date=:date group by movie_id order by count(*) desc limit 1", nativeQuery = true)
    public Integer getMovieHavingMaxShowsOnGivenDate(Date date);
}
