package com.hex.arch.greeting.domain.repository;

import com.hex.arch.greeting.domain.model.Recipient;

import java.util.Optional;
import java.util.UUID;

public interface RecipientRepository {    // second driven port, to save , find and delete recipients
    void save(Recipient recipient);

    Optional<Recipient> findById(UUID id);

    void deleteById(UUID id);
}
