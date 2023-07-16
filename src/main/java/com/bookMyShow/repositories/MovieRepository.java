package com.bookMyShow.repositories;

import com.bookMyShow.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository     // writing this is not necessary, but writing it is a good practice....
public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
