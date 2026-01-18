package com.hex.arch.greeting.client;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import java.util.UUID;

@NullMarked
public record CreateGreetingRequest(
        @NotBlank String message,
        @Nullable UUID recipientId  // Changed from String recipient
) {
    public CreateGreetingRequest {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }
    }
}