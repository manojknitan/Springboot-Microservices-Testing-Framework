package com.learning.microservices;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    /*@GetMapping
    public String getUsers()
    {
        return "Http GET request was sent...";
    }*/

    //Query parameter
    @GetMapping()
    public String getUsersQuery(@RequestParam(value = "page") int pageno, @RequestParam(value = "limit") int limitno)
    {
        return "Http GET request was sent for pageno: "+pageno+" and limitno "+limitno;
    }

    //Path parameter
    @GetMapping(path = "/{userId}")
    public String getUser(@PathVariable String userId)
    {
        return "Http GET request with single user data with id: "+userId;
    }

    @PostMapping
    public String createUser()
    {
        return "Http POST request was sent...";
    }

    @PutMapping
    public String updateUser()
    {
        return "Http PUT request was sent...";
    }

    @DeleteMapping
    public String deleteUser()
    {
        return "Http DELETE request was sent...";
    }

}
