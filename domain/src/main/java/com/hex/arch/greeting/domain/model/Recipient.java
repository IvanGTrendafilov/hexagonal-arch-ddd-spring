package com.hex.arch.greeting.domain.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record Recipient (
        UUID id,
        String firstName,
        String lastName,
        Instant createdAt,
        String address,
        String gender,
        Integer age
) implements Serializable {

}
