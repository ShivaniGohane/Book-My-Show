package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.DTO.request.MovieOwnerSignUpDTO;
import com.bookmyshow.Book.My.Show.Repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.Repository.MovieRepository;
import com.bookmyshow.Book.My.Show.models.ApplicationUser;
import com.bookmyshow.Book.My.Show.models.Hall;
import com.bookmyshow.Book.My.Show.models.Movie;
import com.bookmyshow.Book.My.Show.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public ApplicationUser signUp(MovieOwnerSignUpDTO movieOwnerSignUpDTO){
        ApplicationUser movieOwner = new ApplicationUser();
        movieOwner .setName(movieOwnerSignUpDTO.getName());
        movieOwner .setEmail(movieOwnerSignUpDTO.getEmail());
        movieOwner .setPassword(movieOwnerSignUpDTO.getPassword());
        movieOwner .setType(movieOwnerSignUpDTO.getType().toString());
        movieOwner .setPhoneNumber(movieOwnerSignUpDTO.getPhoneNumber());
        movieOwner .setAge(movieOwnerSignUpDTO.getCompanyAge());
        List<Movie> movies = movieOwnerSignUpDTO.getMovies();
        applicationUserRepository.save(movieOwner);
        for (Movie movie : movies){
            movie.setOwner(movieOwner);
            movieRepository.save(movie);
        }
        //applicationUserRepository.save(movieOwner);
        return movieOwner;
    }

    public Movie getMovieById(UUID id){
        return movieRepository.findById(id).orElse(null);
    }

    public int getTotalTicketCount(Movie movie){
        return movie.getTickets().size();
    }

    public int getBoxOfficeCollection(Movie movie){
        int totalIncome = 0;
        for (Ticket ticket : movie.getTickets()){
            totalIncome = totalIncome + ticket.getShow().getTicketPrice();
        }
        return totalIncome;
    }
}
