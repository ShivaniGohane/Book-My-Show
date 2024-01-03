package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.DTO.request.AddScreenDTO;
import com.bookmyshow.Book.My.Show.DTO.request.AddShowDTO;
import com.bookmyshow.Book.My.Show.DTO.request.HallOwnerSignUpDTO;
import com.bookmyshow.Book.My.Show.DTO.request.RegularUserSignUpDTO;
import com.bookmyshow.Book.My.Show.DTO.response.GeneralMessageDTO;
import com.bookmyshow.Book.My.Show.Exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.Exceptions.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Service.HallService;
import com.bookmyshow.Book.My.Show.models.ApplicationUser;
import com.bookmyshow.Book.My.Show.models.Screen;
import com.bookmyshow.Book.My.Show.models.Show;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Hall API", description = "This controller contains all the hall related service endpoint details")
@RestController
@RequestMapping("/hall")
public class HallController {

    @Autowired
    HallService hallService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody HallOwnerSignUpDTO hallOwnerSignUpDTO){
        ApplicationUser user = hallService.signUp(hallOwnerSignUpDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @Operation(
            summary = "This endpoint enables hall owner such that hall owners can add screens to their respective halls",
            description = "This endpoint enables hall owner such that hall owners can add screens to their respective halls",
            tags = {"tutorials", "post"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Screen got added successfully in the respective hall", content = {@Content(schema = @Schema(implementation = Screen.class), mediaType = "Application/json")}),
            @ApiResponse(responseCode = "404", description = "User does not exist", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")}),
            @ApiResponse(responseCode = "404", description = "Resource Not Found Exception", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")}),
            @ApiResponse(responseCode = "401", description = "User is not authorized to add screens into hall", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")})
    })

    @PostMapping("/addscreen")
    public ResponseEntity addScreen(@RequestBody AddScreenDTO addScreenDTO, @RequestParam("email") String email){
        try {
            hallService.addScreen(addScreenDTO, email);
        }
        catch (UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); // 404
        }
        catch (UnAuthorizedException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED); //401
        }
        catch (ResourceNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new GeneralMessageDTO("Screens added successfully"), HttpStatus.CREATED);
    }

    @PostMapping("/addshow")
    public ResponseEntity addShow(@RequestBody AddShowDTO addShowDTO, @RequestParam("email") String email){
        try {
            Show show = hallService.createShows(addShowDTO, email);
            return new ResponseEntity(show, HttpStatus.CREATED);
        }
        catch (UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); // 404
        }
        catch (UnAuthorizedException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED); //401
        }
        catch (ResourceNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404
        }
    }

}
