package com.bookmyshow.Book.My.Show.Repository;

import com.bookmyshow.Book.My.Show.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    //@Query(value = "select * from application_user where email=:userEmail", nativeQuery = true)
    public ApplicationUser findByEmail(String email);

    @Query(value = "select * from application_user where email=:userEmail", nativeQuery = true)
    public ApplicationUser getUserByEmail(String userEmail);
}
