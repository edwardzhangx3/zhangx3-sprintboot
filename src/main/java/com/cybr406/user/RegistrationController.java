package com.cybr406.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class RegistrationController {

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new RegistrationValidator());
    }

    @Autowired
    JdbcUserDetailsManager userDetailsManager;

    @Autowired
    ProfileRepository authorRepository;

    @Autowired
    User.UserBuilder userBuilder;

    @PostMapping("/signup")
    public ResponseEntity<Profile> signUp(@Valid @RequestBody Registration registration) {
        userDetailsManager.createUser(userBuilder
                .username(registration.getEmail())
                .password(registration.getPassword())
                .roles("BLOGGER")
                .build());

        Profile user = new Profile();
        user.setEmail(registration.getEmail());
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());

        return new ResponseEntity<>(authorRepository.save(user), HttpStatus.CREATED);
    }


}
