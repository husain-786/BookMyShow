package com.bookMyShow.exceptions;

public class MovieNotFoundException extends Exception{
    public MovieNotFoundException(String str){
        super(str);
    }
}
