package com.hex.arch.greeting.client;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record CreateRecipientRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Nullable String address,
        @Nullable String gender,
        @Nullable Integer age
) {
    public CreateRecipientRequest {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
    }
}