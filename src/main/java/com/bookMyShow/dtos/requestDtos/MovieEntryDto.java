package com.bookMyShow.dtos.requestDtos;

import com.bookMyShow.enums.Genre;
import com.bookMyShow.enums.Language;

import lombok.Data;

import java.util.Date;

@Data
public class MovieEntryDto {
    private String name;

    private Double duration;

    private Double rating;

    private Date releaseDate;

    private Genre genre;

    private Language language;
}
