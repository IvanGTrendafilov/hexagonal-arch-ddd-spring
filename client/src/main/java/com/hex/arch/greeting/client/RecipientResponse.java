package com.hex.arch.greeting.client;

import java.time.Instant;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record RecipientResponse(
        UUID id,
        String firstName,
        String lastName,
        Instant createdAt,
        @Nullable String address,
        @Nullable String gender,
        @Nullable Integer age
) implements java.io.Serializable {}