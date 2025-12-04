package com.hex.arch.greeting.domain.service;

import com.hex.arch.greeting.domain.model.Greeting;
import com.hex.arch.greeting.domain.repository.GreetingRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.jmolecules.ddd.annotation.DomainService;
import org.springframework.stereotype.Service;

@NullMarked
@DomainService
@Service
public class GreetingService {
    private final GreetingRepository repository;

    public GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }

    public Greeting createGreeting(String message, @Nullable String recipient) {
        Greeting greeting = new Greeting(
            UUID.randomUUID(),
            message,
            recipient,
            Instant.now()
        );
        repository.save(greeting);
        return greeting;
    }

    public Optional<Greeting> getGreeting(UUID id) {
        return repository.findById(id);
    }

    public void deleteGreeting(UUID id) {
        repository.deleteById(id);
    }
}

