package com.bookmyshow.Book.My.Show.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @JsonIgnore
    @ManyToOne
    ApplicationUser user;
    @JsonIgnore
    @ManyToOne
    Movie movie;
    @JsonIgnore
    @ManyToOne
    Hall hall;
    @JsonIgnore
    @ManyToOne
    Show show;
}
