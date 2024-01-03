package com.bookmyshow.Book.My.Show.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    String directorName;
    String actorName;
    String actressName;
    int imdbRation;
    double duration;//hours;
    @OneToMany(mappedBy = "movie")
    List<Ticket> tickets;
    @ManyToOne
    ApplicationUser owner;
}
