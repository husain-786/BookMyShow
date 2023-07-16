package com.bookMyShow.dtos.requestDtos;

import lombok.Data;

@Data
public class AddUserDto {
    private String name;
    private Integer age;
    private String mobile;
    private String email;
    private String password;
}
