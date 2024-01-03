package com.bookmyshow.Book.My.Show.DTO.request;

import com.bookmyshow.Book.My.Show.enums.UserType;
import com.bookmyshow.Book.My.Show.models.Hall;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HallOwnerSignUpDTO {
    String name;
    String email;
    long phoneNumber;

    String password;
    UserType type;
    List<Hall> halls;
    int companyAge;
}
