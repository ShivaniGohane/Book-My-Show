package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.Service.TicketService;
import com.bookmyshow.Book.My.Show.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("/buyTicket")
    public ResponseEntity buyTicket(@RequestParam String email, @RequestParam UUID showId){
        Ticket ticket = ticketService.buyTicket(email, showId);
        return new ResponseEntity("Ticket booked successfully", HttpStatus.CREATED);
    }
}
