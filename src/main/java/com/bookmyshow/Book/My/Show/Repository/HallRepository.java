package com.bookmyshow.Book.My.Show.Repository;

import com.bookmyshow.Book.My.Show.models.Hall;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HallRepository extends JpaRepository<Hall, UUID> {

}
