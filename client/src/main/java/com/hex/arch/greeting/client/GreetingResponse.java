package com.hex.arch.greeting.client;

import java.time.Instant;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record GreetingResponse(
        UUID id,
        String message,
        @Nullable RecipientResponse recipient,  // Changed from String
        Instant createdAt
) implements java.io.Serializable {

    public record RecipientResponse(
            UUID id,
            String firstName,
            String lastName,
            String address,
            String gender,
            Integer age
    ) implements java.io.Serializable {}
}