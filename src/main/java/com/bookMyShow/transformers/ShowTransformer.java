package com.bookMyShow.transformers;

import com.bookMyShow.dtos.requestDtos.AddShowDto;
import com.bookMyShow.entities.Show;
import lombok.Data;

@Data
public class ShowTransformer {
    public static Show convertDtoToEntity(AddShowDto addShowDto){
        Show show = Show.builder()
                .date(addShowDto.getShowDate())
                .time(addShowDto.getShowTime())
                .build();

        return show;
    }
}
