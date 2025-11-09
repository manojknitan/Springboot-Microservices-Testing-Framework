package com.learning.microservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.microservices.beans.Country;
import com.learning.microservices.controllers.CountryController;
import com.learning.microservices.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.learning")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {CountryControllerMockMvcTests.class})
public class CountryControllerMockMvcTests {

    List<Country> dummyCountries;
    Country country;

    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public void testGetAllCountries() throws Exception {
        dummyCountries = new ArrayList<Country>();
        dummyCountries.add(new Country(1,"India","Delhi"));
        dummyCountries.add(new Country(2, "USA","Washington"));

        when(countryService.getAllCountries()).thenReturn(dummyCountries);

        this.mockMvc.perform(get("/api/countries"))
                .andExpect(status().isFound())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void testGetCountryById() throws Exception {
        int countryId = 3;

        country = new Country(3, "UK", "London");
        when(countryService.getCountryById(countryId)).thenReturn(country);

        this.mockMvc.perform(get("/api/countries/{id}", countryId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London"))
                .andDo(print());
    }

    @Test
    @Order(3)
    public void testGetCountryByName() throws Exception {
        String countryName = "UK";

        country = new Country(3, "UK", "London");
        when(countryService.getCountryByName(countryName)).thenReturn(country);

        this.mockMvc.perform(get("/api/countries/countryname").param("name", "UK"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("UK"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London"))
                .andDo(print());
    }

    @Test
    @Order(4)
    public void testCreateCountry() throws Exception {
        country = new Country(3, "UK", "London");

        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        this.mockMvc.perform(post("/api/countries/create")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void testModifyCountry() throws Exception {
        int countryId = 4;

        country = new Country(4, "Japan", "Tokyo");

        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        this.mockMvc.perform(put("/api/countries/update/{id}", countryId)
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
                .andDo(print());
    }

    @Test
    @Order(6)
    public void testRemoveCountry() throws Exception {
        int countryId = 5;

        country = new Country(5, "Italy", "Rome");

        when(countryService.getCountryById(countryId)).thenReturn(country);

        this.mockMvc.perform(delete("/api/countries/delete/{id}", countryId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
