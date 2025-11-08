package com.learning.microservices;

import com.learning.microservices.beans.Country;
import com.learning.microservices.controllers.AddResponse;
import com.learning.microservices.repositories.CountryRepository;
import com.learning.microservices.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {CountryServiceTests.class})
public class CountryServiceTests {

    public List<Country> dummyCountries;

    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryService countryService;

    @Test
    @Order(1)
    public void testGetAllCountries()
    {
        dummyCountries = new ArrayList<Country>();
        dummyCountries.add(new Country(1, "India", "Delhi"));
        dummyCountries.add(new Country(2, "USA", "Washington"));

        when(countryRepository.findAll()).thenReturn(dummyCountries);
        assertEquals(2, countryService.getAllCountries().size());
    }

    @Test
    @Order(2)
    public void testGetCountryById()
    {
        int countryId = 3;

        dummyCountries = new ArrayList<Country>();
        dummyCountries.add(new Country(1, "India", "Delhi"));
        dummyCountries.add(new Country(2, "USA", "Washington"));
        dummyCountries.add(new Country(3, "UK", "London"));

        when(countryRepository.findAll()).thenReturn(dummyCountries);
        assertEquals(countryId, countryService.getCountryById(countryId).getId());
    }

    @Test
    @Order(3)
    public void testGetCountryByName()
    {
        String countryName = "India";

        dummyCountries = new ArrayList<Country>();
        dummyCountries.add(new Country(1, "India", "Delhi"));
        dummyCountries.add(new Country(2, "USA", "Washington"));
        dummyCountries.add(new Country(3, "UK", "London"));

        when(countryRepository.findAll()).thenReturn(dummyCountries);
        assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());
    }

    @Test
    @Order(4)
    public void testAddCountry()
    {
        Country country = new Country(3, "Germany","Berlin");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.addCountry(country));
    }

    @Test
    @Order(5)
    public void testUpdateCountry()
    {
        Country country = new Country(4, "France","Paris");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.updateCountry(country));
    }

    @Test
    @Order(6)
    public void testDeleteCountry()
    {
        int countryId = 5;

        countryRepository.deleteById(countryId);
        verify(countryRepository, times(1)).deleteById(countryId);
    }

}
