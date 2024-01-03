package com.bookmyshow.Book.My.Show.DTO.request;

import com.bookmyshow.Book.My.Show.models.Screen;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddScreenDTO {
    List<Screen> screens;
    UUID hallId;
}
