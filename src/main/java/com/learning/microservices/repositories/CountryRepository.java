package com.learning.microservices.repositories;

import com.learning.microservices.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer>
{

}
