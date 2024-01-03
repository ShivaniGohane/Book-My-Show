package com.bookmyshow.Book.My.Show.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String screenName;
    @JsonIgnore
    @ManyToOne
    Hall hall;
    int screenCapacity;
    boolean status;
    String type;
}
