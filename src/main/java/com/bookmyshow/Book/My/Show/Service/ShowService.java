package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.Repository.ShowRepository;
import com.bookmyshow.Book.My.Show.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShowService {
    @Autowired
    ShowRepository showRepository;

    public void createShow(Show show){
        showRepository.save(show);
    }

    public List<Show> getShowByMovieId(UUID movieId){
        return showRepository.getShowByMovieId(movieId);
    }

    public List<Show> getShowByHallId(UUID hallId){
        return showRepository.getShowByHallId(hallId);
    }

    public List<Show> getShowByMovieIdAndHallId(UUID hallId, UUID movieId){
        return showRepository.getShowByHallIdAndMovieId(hallId, movieId);
    }

    public Show getShowByShowId(UUID showId){
        return showRepository.findById(showId).orElse(null);
    }

    /*
       Decrease Available ticket count for a particular show.
     */
    public void updateAvailableTicketCount(Show show){
        int updateAvailableTicket = show.getAvailableTickets()-1;
        UUID showId = show.getId();
        showRepository.updateAvailableTicketCount(showId, updateAvailableTicket);
    }
}
