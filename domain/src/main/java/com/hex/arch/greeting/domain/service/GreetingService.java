package com.hex.arch.greeting.domain.service;

import com.hex.arch.greeting.domain.model.Greeting;
import com.hex.arch.greeting.domain.model.Recipient;
import com.hex.arch.greeting.domain.driven.port.ForGreetingPersistence;
import com.hex.arch.greeting.domain.repository.RecipientRepository;
import java.time.Instant;
import java.util.List;
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
    private final RecipientRepository recipientRepository;

    public GreetingService(ForGreetingPersistence forGreetingPersistence, RecipientRepository recipientRepository) {
        this.forGreetingPersistence = forGreetingPersistence;
        this.recipientRepository = recipientRepository;
    }

    public Greeting createGreeting(String message, @Nullable UUID recipientId) {
        Recipient recipient = null;
        if (recipientId != null) {
            recipient = recipientRepository.findById(recipientId)
                    .orElseThrow(() -> new IllegalArgumentException("Recipient not found: " + recipientId));
        }

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

    public List<Greeting> getAllGreetings() {
        return forGreetingPersistence.findAll();
    }
}