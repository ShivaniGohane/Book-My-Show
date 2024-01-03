package com.bookmyshow.Book.My.Show.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @JsonIgnore
    @ManyToOne
    Hall hall;
    @JsonIgnore
    @ManyToOne
    Movie movie;
    @JsonIgnore
    @ManyToOne
    Screen screen;
    int availableTickets;
    Date startTime;
    Date endTime;
    int ticketPrice;
    @OneToMany(mappedBy = "show")
    List<Ticket> tickets;
}
