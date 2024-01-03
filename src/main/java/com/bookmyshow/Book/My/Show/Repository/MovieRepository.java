package com.bookmyshow.Book.My.Show.Repository;

import com.bookmyshow.Book.My.Show.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
