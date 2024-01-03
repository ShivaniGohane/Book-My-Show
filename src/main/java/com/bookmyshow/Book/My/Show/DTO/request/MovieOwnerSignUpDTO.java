package com.bookmyshow.Book.My.Show.DTO.request;

import com.bookmyshow.Book.My.Show.enums.UserType;
import com.bookmyshow.Book.My.Show.models.Movie;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieOwnerSignUpDTO {
    String name;
    String email;
    long phoneNumber;
    String password;
    UserType type;
    int companyAge;
    List<Movie> movies;
}
