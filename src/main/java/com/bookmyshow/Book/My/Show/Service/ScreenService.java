package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.Repository.ScreenRepository;
import com.bookmyshow.Book.My.Show.models.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScreenService {
    @Autowired
    ScreenRepository screenRepository;

    public void registerScreen(Screen screen){
        screenRepository.save(screen);
    }

    public void bookScreen(UUID id){
        screenRepository.bookScreen(id);
    }
}
