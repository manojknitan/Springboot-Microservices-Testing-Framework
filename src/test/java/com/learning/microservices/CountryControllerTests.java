package com.learning.microservices;

import com.learning.microservices.beans.Country;
import com.learning.microservices.controllers.CountryController;
import com.learning.microservices.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {CountryControllerTests.class})
public class CountryControllerTests {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> dummyCountries;
    Country country;

    @Test
    @Order(1)
    public void testGetCountries()
    {
        dummyCountries = new ArrayList<Country>();
        dummyCountries.add(new Country(1,"India","Delhi"));
        dummyCountries.add(new Country(2, "USA","Washington"));

        when(countryService.getAllCountries()).thenReturn(dummyCountries);
        ResponseEntity<List<Country>> responseEntity = countryController.getCountries();

        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    @Order(2)
    public void testGetCountryById()
    {
        int countryId=3;

        country = new Country(3, "UK","London");
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> responseEntity = countryController.getSingleCountryById(countryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(countryId, responseEntity.getBody().getId());
    }

    @Test
    @Order(3)
    public void testGetCountryByName()
    {
        String countryName = "Russia";

        country = new Country(4, "Russia", "Moscow");
        when(countryService.getCountryByName(countryName)).thenReturn(country);
        ResponseEntity<Country> responseEntity = countryController.getSingleCountryByParam(countryName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(countryName, responseEntity.getBody().getCountryName());
    }

    @Test
    @Order(4)
    public void testCreateCountry()
    {
        country = new Country(5, "Italy","Rome");
        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> responseEntity = countryController.createCountry(country);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(country, responseEntity.getBody());
    }

    @Test
    @Order(5)
    public void testModifyCountry()
    {
        int countryId = 6;
        country = new Country(6, "Japan","Tokyo");
        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> responseEntity = countryController.modifyCountry(countryId, country);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(6,responseEntity.getBody().getId());
        assertEquals("Japan",responseEntity.getBody().getCountryName());
        assertEquals("Tokyo",responseEntity.getBody().getCountryCapital());
    }

    @Test
    @Order(6)
    public void testRemoveCountry()
    {
        int countryId = 7;

        country = new Country(7, "France","Paris");
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> responseEntity = countryController.removeCountry(countryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
