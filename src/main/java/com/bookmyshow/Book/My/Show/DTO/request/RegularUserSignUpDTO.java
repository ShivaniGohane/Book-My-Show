package com.bookmyshow.Book.My.Show.DTO.request;

import com.bookmyshow.Book.My.Show.enums.UserType;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegularUserSignUpDTO {
    String name;
    String email;
    long phoneNumber;
    String password;
    UserType type; //movieOwner, HallOwner, RegularUser
    int age;
}
