package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.Service.ShowService;
import com.bookmyshow.Book.My.Show.models.Show;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/show")
public class ShowController{

    @Autowired
    ShowService showService;

    @Parameters({
            @Parameter(name = "movieId", description = "It accepts only UUID", required = true),
            @Parameter(name = "hallId", description = "It accepts only UUID")
    })

    @GetMapping("/search")
    public ResponseEntity searchShowByMovieId(@RequestParam(required = false) UUID movieId, @RequestParam(required = false) UUID hallId){
        if(movieId!=null && hallId!=null){
            //search for both movieId and HallId
            List<Show> shows = showService.getShowByMovieIdAndHallId(hallId, movieId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }
        else if(movieId==null && hallId!=null){
            //search for hallId
            List<Show> shows = showService.getShowByHallId(hallId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }
        else if(movieId!=null && hallId==null){
            List<Show> shows = showService.getShowByMovieId(movieId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Please pass at least one param.", HttpStatus.OK);
        }
    }

}
