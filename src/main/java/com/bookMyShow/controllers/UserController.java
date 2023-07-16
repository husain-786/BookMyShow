package com.bookMyShow.controllers;

import com.bookMyShow.dtos.requestDtos.AddUserDto;
import com.bookMyShow.dtos.responseDtos.ResultDto;
import com.bookMyShow.dtos.responseDtos.UserResponseDto;
import com.bookMyShow.entities.User;
import com.bookMyShow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Adding new user in the database..........
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody AddUserDto addUserDto){
        // Setting the header for the response entity........
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        try {
            // response body from the service layer.......
            String response = userService.addUser(addUserDto);

            // setting header for the response entity.......
            header.add("SUCCESS", "User Created Successfully");

            return new ResponseEntity<>(response, header, HttpStatus.CREATED);
        }
        catch (Exception e){
            // setting header for the response entity.......
            header.add("FAILURE", "Something Went Wrong");

            return new ResponseEntity<>(e.getMessage(), header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Finding the oldest user.........
    @GetMapping("oldestUser")
    public ResponseEntity<UserResponseDto> getOldestUser(){
        // Initializing ResponseEntity for the
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        try {
            // Response Body:- calling the service layer for the data needed......
            UserResponseDto userResponseDto = userService.getResponseDto();

            // setting the ResultDto data, which will tell the status code and the status description.........
            userResponseDto.setResultDto(new ResultDto("200", "SUCCESS", "User Found Succesfully"));

            // Setting headers for the response entity......
            header.add("SUCCESS", "Data Found and Fetched Successfully");

            return new ResponseEntity<>(userResponseDto, header, HttpStatus.OK);
        }
        catch(Exception e){
            UserResponseDto userResponseDto = new UserResponseDto();

            // setting the ResultDto data, which will tell the status code and the status description.........
            userResponseDto.setResultDto(new ResultDto("500", "FAILURE", "No User Found"));

            // Setting headers for the response entity......
            header.add("FAILURE", "No User Found");

            return new ResponseEntity<>(userResponseDto, header, HttpStatus.NOT_FOUND);
        }
    }

    // finding all the users above a given age..........
    @GetMapping("/findAllUserAboveGivenAge")
    public ResponseEntity<List<UserResponseDto>> findAllUserAboveGivenAge(@RequestParam Integer age){
        try{
            List<UserResponseDto> userResponseDtoList = userService.findAllUserAboveGivenAge(age);
            return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
