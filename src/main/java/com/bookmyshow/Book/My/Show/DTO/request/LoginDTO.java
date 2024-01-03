package com.bookmyshow.Book.My.Show.DTO.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {
    String email;
    String password;
}
