package com.hex.arch.greeting.domain.service;

import com.hex.arch.greeting.domain.model.Greeting;
import com.hex.arch.greeting.domain.driven.port.ForGreetingPersistence;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@NullMarked
@Service
public class GreetingService {
    private final ForGreetingPersistence forGreetingPersistence;

    public GreetingService(ForGreetingPersistence forGreetingPersistence) {
        this.forGreetingPersistence = forGreetingPersistence;
    }

    public Greeting createGreeting(String message, @Nullable String recipient) {
        Greeting greeting = new Greeting(
            UUID.randomUUID(),
            message,
            recipient,
            Instant.now()
        );
        forGreetingPersistence.save(greeting);
        return greeting;
    }
    @Cacheable(value = "greetings", key = "#p0")
    public Optional<Greeting> getGreeting(UUID id) {
        System.out.println("🔍 SERVICE: Fetching from DATABASE for ID: " + id);
        return forGreetingPersistence.findById(id);
    }
    @CacheEvict(value = "greetings", key = "#p0")
    public void deleteGreeting(UUID id) {
        forGreetingPersistence.deleteById(id);
    }
}

