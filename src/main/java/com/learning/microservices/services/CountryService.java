package com.learning.microservices.services;

import com.learning.microservices.beans.Country;
import com.learning.microservices.controllers.AddResponse;
import com.learning.microservices.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCountries()
    {
        List<Country> countries = countryRepository.findAll();
        return countries;
    }

    public Country getCountryById(int id)
    {
        List<Country> countries = countryRepository.findAll();
        Country country = null;
        for(Country con: countries)
        {
            if(con.getId() == id)
                country = con;
        }
        return country;
    }

    public Country getCountryByName(String countryName)
    {
        List<Country> countries = countryRepository.findAll();
        Country country = null;

        for(Country con : countries)
        {
            if(con.getCountryName().equalsIgnoreCase(countryName))
                country = con;
        }
        return country;
    }

    public Country addCountry(Country country)
    {
        country.setId(getNextId());
        countryRepository.save(country);
        return country;
    }

    public int getNextId()
    {
       return countryRepository.findAll().size()+1;
    }

    public Country updateCountry(Country country)
    {
        countryRepository.save(country);
        return country;
    }

    public AddResponse deleteCountry(int id)
    {
        countryRepository.deleteById(id);

        AddResponse response = new AddResponse();
        response.setMsg("Country's data deleted successfully.");
        response.setId(id);
        return response;
    }
    
}
