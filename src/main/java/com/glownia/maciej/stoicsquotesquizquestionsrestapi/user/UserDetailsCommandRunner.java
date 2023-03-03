package com.glownia.maciej.stoicsquotesquizquestionsrestapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsCommandRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    private UserDetailsRepository repository;

    public UserDetailsCommandRunner(UserDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetails("Mac", "Admin"));
        repository.save(new UserDetails("Tom", "Admin"));
        repository.save(new UserDetails("Bob", "User"));

//        List<UserDetails> users = repository.findAll();
          List<UserDetails> users = repository.findByRole("Admin");

        users.forEach(user -> logger.info(user.toString()));
    }
}
