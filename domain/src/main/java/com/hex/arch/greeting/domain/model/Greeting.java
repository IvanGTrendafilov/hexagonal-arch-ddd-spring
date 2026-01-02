package com.hex.arch.greeting.domain.model;

import java.time.Instant;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.jmolecules.ddd.annotation.AggregateRoot;

@NullMarked
@AggregateRoot
public record Greeting(
    UUID id,
    String message,
    @Nullable String recipient,
    Instant createdAt
) implements java.io.Serializable {
    public Greeting {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
    }

    public Greeting withMessage(String newMessage) {
        return new Greeting(id, newMessage, recipient, createdAt);
    }

    public Greeting withRecipient(String newRecipient) {
        return new Greeting(id, message, newRecipient, createdAt);
    }
}

