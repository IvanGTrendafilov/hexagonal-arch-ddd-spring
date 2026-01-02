package com.hex.arch.greeting.client;

import java.time.Instant;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record GreetingResponse(
    UUID id,
    String message,
    @Nullable String recipient,
    Instant createdAt
) implements java.io.Serializable {}

