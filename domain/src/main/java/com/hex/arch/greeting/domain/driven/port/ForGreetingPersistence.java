package com.hex.arch.greeting.domain.driven.port;

import com.hex.arch.greeting.domain.model.Greeting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

@NullMarked
@Repository
public interface ForGreetingPersistence {     // first driven port, to save and find greetings in our database
    void save(Greeting greeting);

    Optional<Greeting> findById(UUID id);

    void deleteById(UUID id);

    List<Greeting> findAll();
}

