package com.bookMyShow.services;

import com.bookMyShow.dtos.requestDtos.AddUserDto;
import com.bookMyShow.dtos.responseDtos.UserResponseDto;
import com.bookMyShow.entities.User;
import com.bookMyShow.exceptions.NoUserFoundException;
import com.bookMyShow.transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookMyShow.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String addUser(AddUserDto addUserDto) {
        User user = UserTransformer.convertDtoToEntity(addUserDto);

        userRepository.save(user);

        return "User has been added successfully";
    }

    public UserResponseDto getResponseDto() throws NoUserFoundException {
        // Dto prevents from exposing the primary key......
        // Dto Prevents from infinite recursion incase if it occurs......

        int max = 0;
        User maxAgeUser = null;
        List<User> userList = userRepository.findAll();

        // if no user is found then throw No user  found exception.........
        if (userList.size() == 0){
            throw new NoUserFoundException("No user found");
        }

        for (User user: userList){
            if (max < user.getAge()){
                max = user.getAge();
                maxAgeUser = user;
            }
        }

        UserResponseDto userResponseDto = UserTransformer.convertEntityToDto(maxAgeUser);
        return userResponseDto;
    }

    public List<UserResponseDto> findAllUserAboveGivenAge(Integer age) throws NoUserFoundException {
        List<User> userList = userRepository.findAllUserAboveGivenAge(age);

        if (userList.size() == 0){
            throw new NoUserFoundException("No user found");
        }

        // Converting user entity list into List of UserResponseDto.........
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user: userList){
            userResponseDtoList.add(UserTransformer.convertEntityToDto(user));
        }

        return userResponseDtoList;
    }
}
