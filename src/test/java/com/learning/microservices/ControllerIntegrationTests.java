package com.learning.microservices;

import com.learning.microservices.beans.Country;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {

    @Disabled("Skipping until fix.")
    @Test
    @Order(1)
    public void testGetAllCountries() throws JSONException
    {
        String expected = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"countryName\": \"India\",\n" +
                "        \"countryCapital\": \"Delhi\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"countryName\": \"USA\",\n" +
                "        \"countryCapital\": \"Washington\"\n" +
                "    }\n" +
                "]";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/countries/", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(2)
    public void testGetCountriesById() throws JSONException
    {
        String expected = "{\n" +
                "    \"id\": 1,\n" +
                "    \"countryName\": \"India\",\n" +
                "    \"countryCapital\": \"Delhi\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/countries/1", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(3)
    public void testGetCountriesByName() throws JSONException
    {
        String expected = "{\n" +
                "    \"id\": 1,\n" +
                "    \"countryName\": \"India\",\n" +
                "    \"countryCapital\": \"Delhi\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/countries/countryname?name=India", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(4)
    public void testAddCountry() throws JSONException
    {
        String expected = "{\n" +
                "    \"id\": 10,\n" +
                "    \"countryName\": \"Germany\",\n" +
                "    \"countryCapital\": \"Berlin\"\n" +
                "}";
        Country country = new Country(10, "Germany", "Berlin");

        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> entity = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/countries/create",entity ,String.class);
        System.out.println(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(5)
    public void testUpdateCountry() throws JSONException
    {
        String expected = "{\n" +
                "    \"id\": 5,\n" +
                "    \"countryName\": \"J\",\n" +
                "    \"countryCapital\": \"B\"\n" +
                "}";
        Country country = new Country(5, "Germany", "Berlin");

        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> entity = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/countries/update/3",HttpMethod.PUT,entity ,String.class);
        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(6)
    public void testDeleteCountry() throws JSONException
    {
        String expected = "{\n" +
                "    \"id\": 4,\n" +
                "    \"countryName\": \"Germany\",\n" +
                "    \"countryCapital\": \"Berlin\"\n" +
                "}";
        Country country = new Country(4, "Germany", "Berlin");

        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> entity = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/countries/delete/4",HttpMethod.DELETE,entity ,String.class);
        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        //JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}
