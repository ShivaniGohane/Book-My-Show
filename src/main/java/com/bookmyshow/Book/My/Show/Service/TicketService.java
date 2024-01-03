package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.Exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.Exceptions.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.Repository.TicketRepository;
import com.bookmyshow.Book.My.Show.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    ShowService showService;

    @Autowired
    MovieService movieService;

    @Autowired
    HallService hallService;

    @Autowired
    MailService mailService;

    @Autowired
    BarCodeGenerationService barCodeGenerationService;

    public Ticket buyTicket(String email, UUID showId){
        // Get User by Email Id
        ApplicationUser user = applicationUserRepository.findByEmail(email);
        // If user is null -> That means no user is existing with this email Id
        if(user==null){
            throw new UserDoesNotExistException(String.format("User with email Id %s does not exist in system.", email));
        }
        // Check user has required access or not-> If user is type Movie Owner & HallOwner then they don't have access
        if(!user.getType().equals("RegularUser")){
            throw new UnAuthorizedException(String.format("User with email %s does not have required access", email));
        }
        // Validate show with whatever id we are getting in function parameter is existing in our system or not.
        Show show = showService.getShowByShowId(showId);
        if(show==null){
            throw new ResourceNotExistException(String.format("Show with id %s does not exist in our system.", showId));
        }
        // We have to decrease ticket  count for this particular showId as we are buying one ticket.
        showService.updateAvailableTicketCount(show);
        Ticket ticket = new Ticket();
        ticket.setHall(show.getHall());
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setUser(user);

        Movie movie = movieService.getMovieById(show.getMovie().getId());
        Hall hall = hallService.getHallById(show.getHall().getId());

        // First send ticket details to user
        // UserTicket message

        String userMessage = String.format("hey %s, \n" +
                "Congratulation!! your ticket got booked on our application. Below are your ticket details: \n" +
                "1. Movie name - %s\n "+
                "2. Hall Name - %s\n + " +
                "3. Hall Address - %s\n" +
                "4. Date And Timing - %s\n " +
                "5. Ticket Price - %d\n" +
                "\n Hope you will enjoy your show, All the Best" +
                "Accio booking Application", user.getName(), movie.getName(), hall.getName(), hall.getAddress(), show.getStartTime().toString(), show.getTicketPrice());
        String userSub = String.format("Congratulation!! %s Your Ticket got generated !!", user.getName());
        try {
            barCodeGenerationService.generateQR(userMessage);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        mailService.generateMail(user.getEmail(), userSub, userMessage, "./src/main/resources/static/QRCode.png");

        int totalTicket = movieService.getTotalTicketCount(movie);
        int totalIncome = movieService.getBoxOfficeCollection(movie);

        String movieMessage = String.format("Hi %s\n" +
                "Congratulation!! your ticket got sold\n" +
                "TotalTicketsSold : %d " +
                "TotalIncome : %d", movie.getOwner().getName(), totalTicket, totalIncome);

        String movieSubject = String.format("Congratulations!! %s One more ticket sold", movie.getOwner().getName());
        mailService.generateMail(movie.getOwner().getEmail(), movieSubject, movieMessage);
        return ticketRepository.save(ticket);
    }
}
