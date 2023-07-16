package com.bookMyShow.transformers;

import com.bookMyShow.dtos.requestDtos.AddUserDto;
import com.bookMyShow.dtos.responseDtos.UserResponseDto;
import com.bookMyShow.entities.User;

public class UserTransformer {
    public static User convertDtoToEntity(AddUserDto addUserDto){
        User user = new User();

        // First Method to convert entity to dto.......
        user.setName(addUserDto.getName());
        user.setAge(addUserDto.getAge());
        user.setEmail(addUserDto.getEmail());
        user.setMobile(addUserDto.getMobile());
        user.setPassword(addUserDto.getPassword());

        // We can simply create entities object by using dtos by using "@Builder", it reduces complecities
        // and make the code clear. For that we need to declare or use "@Builder" on the entity of which want
        // to create the object.....

        // Second Method to convert entity to dto.......
        User user1 = User.builder()
                .name(addUserDto.getName())
                .age(addUserDto.getAge())
                .email(addUserDto.getEmail())
                .mobile(addUserDto.getMobile())
                .password(addUserDto.getPassword())
                .build();

        return user1;
    }

    public static UserResponseDto convertEntityToDto(User maxAgeUser){
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name(maxAgeUser.getName())
                .age(maxAgeUser.getAge())
                .mobile(maxAgeUser.getMobile())
                .password(maxAgeUser.getPassword())
                .build();

        return userResponseDto;
    }
}
