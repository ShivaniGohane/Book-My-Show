package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.DTO.request.AddScreenDTO;
import com.bookmyshow.Book.My.Show.DTO.request.AddShowDTO;
import com.bookmyshow.Book.My.Show.DTO.request.HallOwnerSignUpDTO;
import com.bookmyshow.Book.My.Show.Exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.Exceptions.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.Repository.HallRepository;
import com.bookmyshow.Book.My.Show.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HallService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    RegularUserService regularUserService;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    ScreenService screenService;

    @Autowired
     MovieService movieService;

    @Autowired
    ShowService showService;
    public ApplicationUser signUp(HallOwnerSignUpDTO hallOwnerSignUpDTO){
        ApplicationUser hallOwner = new ApplicationUser();
        hallOwner.setName(hallOwnerSignUpDTO.getName());
        hallOwner.setEmail(hallOwnerSignUpDTO.getEmail());
        hallOwner.setPassword(hallOwnerSignUpDTO.getPassword());
        hallOwner.setType(hallOwnerSignUpDTO.getType().toString());
        hallOwner.setPhoneNumber(hallOwnerSignUpDTO.getPhoneNumber());
        hallOwner.setAge(hallOwnerSignUpDTO.getCompanyAge());
        List<Hall> halls = hallOwnerSignUpDTO.getHalls();
        //System.out.println(hallOwner.getId());
        applicationUserRepository.save(hallOwner);
        //System.out.println(hallOwner.getId());
        for (Hall hall : halls){
            hall.setOwner(hallOwner);
            hallRepository.save(hall);
        }
        //applicationUserRepository.save(hallOwner);
        return hallOwner;
    }

    public Hall getHallById(UUID id){
        return hallRepository.findById(id).orElse(null);
    }

    public void addScreen(AddScreenDTO addScreenDTO, String email){
        List<Screen> screens = addScreenDTO.getScreens();
        UUID hallId = addScreenDTO.getHallId();
        ApplicationUser user = regularUserService.getUserByEmail(email);
        if(user==null){
            throw new UserDoesNotExistException("User with this email does not exist.");
        }
        if(!user.getType().equals("HallOwner")){
            throw new UnAuthorizedException("User is not access to perform a task.");
        }
        Hall hall = getHallById(hallId);
        if(hall == null){
            throw new ResourceNotExistException(String.format("Hall with id %s does not exist in System", hallId.toString())); // This hal, id variable is the caiablewe are passing in postman
        }
        if(hall.getOwner().getId() != user.getId()){
            throw new UnAuthorizedException("User does not own this hallId");
        }

        for (Screen screen : screens){
            screen.setHall(hall);
            screenService.registerScreen(screen);
        }
    }

    public Show createShows(AddShowDTO addShowDTO, String email){
        // Validate email exist in Application User Table or not.
        ApplicationUser user = applicationUserRepository.findByEmail(email);
        if(user == null){
            throw new UserDoesNotExistException(String.format("User with email id %s does not exist in system.", email));
        }
        // validate the type of the user is the type of the user is hallOwner will do nothing
        // else we will throw UnAuthorized exception
        if(!user.getType().equals("HallOwner")){
            throw new UnAuthorizedException(String.format("User with emailId %s does not have required permission to perform this action.", email));
        }
        UUID hallId = addShowDTO.getHallId();
        // validate this particular hallId is existing in our system or not.
        // If not throw ResourceNotFoundException because hall is a resource here and it is not existing
        Hall hall = getHallById(hallId);
        if(hall == null){
            throw new ResourceNotExistException(String.format("Hall with id %s does not exist in System.", hallId.toString()));
        }
        // validate user owns this hall or  not
        // if user does not own this hall then user is unAuthorized to create show in this hall
        if(hall.getOwner().getId() != user.getId()){
            throw new UnAuthorizedException(String.format("User with emailId %s does not own hall with HallId %s.", email, hallId.toString()));
        }
        // validate movieID which we got from AddShowDTO is existing in our system not.
        // If not then we need to throw ResourceNotFound Exception because movie is a resource in our Application
        UUID movieId = addShowDTO.getMovieId();
        Movie movie = movieService.getMovieById(movieId);
        if(movie == null){
            throw new ResourceNotExistException(String.format("Movie with movieID %s does not exist in system.", movieId.toString()));
        }
        // All the validation passed
        // 1. Get Screens that are not occupied
        List<Screen> screens = new ArrayList<>();
        for(Screen screen : hall.getScreens()){
            if(screen.isStatus() == false){
                screens.add(screen);
            }
        }
        // If after the for loop list screens is having size as zero then we should throw one exception
        // Resource Not found exception
        if(screens.size() == 0) {
            throw new ResourceNotExistException(String.format("Hall with hallId %s does not have any unoccupied screen", hallId.toString()));
        }
        Screen screen = screens.get(0);
        //Setting all the properties for the show
        Show show = new Show();
        show.setHall(hall);
        show.setMovie(movie);
        show.setAvailableTickets(screen.getScreenCapacity());
        show.setTicketPrice(addShowDTO.getTicketPrice());
        show.setScreen(screen); // allocating screen to show
        Date startDateTime = new Date();
        startDateTime.setHours(addShowDTO.getHour());
        startDateTime.setMinutes(addShowDTO.getMinute());
        startDateTime.setSeconds(0);
        // 22:00 -> EndTime -> 22 +  Movie Duration(4) -> 22 + 4 -> 26:00 -> 26%24 -> 02:00
        Date endDateTime = new Date();//22:12:23 21:49:07.8888
        int hour = (int)(addShowDTO.getHour() + movie.getDuration())%24;
        endDateTime.setHours(hour);
        endDateTime.setMinutes(addShowDTO.getMinute());
        show.setStartTime(startDateTime);
        show.setEndTime(endDateTime);
        // Mark status of screen as true. Such that no other show can book that.
        screenService.bookScreen(screen.getId());
        showService.createShow(show);
        return show;
    }
}
