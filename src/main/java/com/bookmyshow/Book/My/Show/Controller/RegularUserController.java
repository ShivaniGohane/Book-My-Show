package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.DTO.request.LoginDTO;
import com.bookmyshow.Book.My.Show.DTO.request.RegularUserSignUpDTO;
import com.bookmyshow.Book.My.Show.DTO.response.GeneralMessageDTO;
import com.bookmyshow.Book.My.Show.Exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.Exceptions.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Service.RegularUserService;
import com.bookmyshow.Book.My.Show.models.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RegularUserController {

    @Autowired
    RegularUserService regularUserService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody RegularUserSignUpDTO regularUserSignUpDTO){
        ApplicationUser user = regularUserService.signup(regularUserSignUpDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        try {
            ApplicationUser user = regularUserService.login(loginDTO);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch (UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (UnAuthorizedException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

}
