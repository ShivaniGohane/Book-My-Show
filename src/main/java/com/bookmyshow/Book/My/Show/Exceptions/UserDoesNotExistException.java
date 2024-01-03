package com.bookmyshow.Book.My.Show.Exceptions;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(String msg){
        super(msg);
    }
}
