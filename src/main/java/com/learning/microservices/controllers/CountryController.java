package com.learning.microservices.controllers;


import com.learning.microservices.beans.Country;
import com.learning.microservices.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries()
    {
        try {
            List<Country> countries = countryService.getAllCountries();
            return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getSingleCountryById(@PathVariable(value = "id") int id)
    {
        try {
            Country country = countryService.getCountryById(id);
            return new ResponseEntity<Country>(country, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/countries/countryname")
    public ResponseEntity<Country> getSingleCountryByParam(@RequestParam(value = "name") String cname)
    {
        try{
            Country country = countryService.getCountryByName(cname);
            return new ResponseEntity<Country>(country,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/countries/create")
    public ResponseEntity<Country> createCountry(@RequestBody Country country)
    {
        try{
            country = countryService.addCountry(country);
            return new ResponseEntity<Country>(country, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/countries/update/{id}")
    public ResponseEntity<Country> modifyCountry(@PathVariable(value = "id") int id ,@RequestBody Country country)
    {
        try
        {
            Country existingCountry = countryService.getCountryById(id);
            existingCountry.setCountryName(country.getCountryName());
            existingCountry.setCountryCapital(country.getCountryCapital());
            Country updatedCountry = countryService.updateCountry(existingCountry);

            return new ResponseEntity<Country>(updatedCountry,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("countries/delete/{id}")
    public ResponseEntity<Country> removeCountry(@PathVariable(value = "id") int id)
    {
        Country country = null;
        try
        {
            country = countryService.getCountryById(id);
            countryService.deleteCountry(id);
        }
        catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
