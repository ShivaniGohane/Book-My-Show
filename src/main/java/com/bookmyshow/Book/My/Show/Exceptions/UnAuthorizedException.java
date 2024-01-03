package com.bookmyshow.Book.My.Show.Exceptions;

public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String msg){
        super(msg);
    }
}
