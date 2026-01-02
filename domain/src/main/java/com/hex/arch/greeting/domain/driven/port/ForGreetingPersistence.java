package com.hex.arch.greeting.domain.driven.port;

import com.hex.arch.greeting.domain.model.Greeting;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

@NullMarked
@Repository
public interface ForGreetingPersistence {
    void save(Greeting greeting);

    Optional<Greeting> findById(UUID id);

    void deleteById(UUID id);
}

