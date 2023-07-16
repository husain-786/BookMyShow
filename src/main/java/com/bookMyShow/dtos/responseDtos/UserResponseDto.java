package com.bookMyShow.dtos.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    // User Detail.....
    private String name;
    private Integer age;
    private String mobile;
    private String password;

    // Message Detail......
    private ResultDto resultDto;
}
