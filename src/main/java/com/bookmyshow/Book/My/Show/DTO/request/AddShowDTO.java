package com.bookmyshow.Book.My.Show.DTO.request;

import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddShowDTO {
    int hour; // 13
    int minute; //00
    int ticketPrice;
    UUID movieId;
    UUID hallId;
}
