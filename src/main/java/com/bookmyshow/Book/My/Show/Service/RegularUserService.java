package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.DTO.request.LoginDTO;
import com.bookmyshow.Book.My.Show.DTO.request.RegularUserSignUpDTO;
import com.bookmyshow.Book.My.Show.Exceptions.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.models.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RegularUserService{
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    public ApplicationUser signup(RegularUserSignUpDTO regularUserSignUpDTO){
        ApplicationUser user = new ApplicationUser();
        user.setAge(regularUserSignUpDTO.getAge());
        user.setName(regularUserSignUpDTO.getName());
        user.setEmail(regularUserSignUpDTO.getEmail());
        user.setPassword(regularUserSignUpDTO.getPassword());
        user.setPhoneNumber(regularUserSignUpDTO.getPhoneNumber());
        user.setType(regularUserSignUpDTO.getType().toString());
        applicationUserRepository.save(user);
        return user;
    }

    public ApplicationUser login(LoginDTO loginDTO){
        ApplicationUser user = getUserByEmail(loginDTO.getEmail());
        if(user==null){
            throw new UserDoesNotExistException(String.format("User with email id %s does not exist in system.", loginDTO.getEmail()));
        }
        if(!user.getPassword().equals(loginDTO.getPassword())){
            throw new UnAuthorizedException("Wrong Credential.");
        }
        return user;
    }

    public ApplicationUser getUserByEmail(String email){
        return applicationUserRepository.findByEmail(email);
    }

}
