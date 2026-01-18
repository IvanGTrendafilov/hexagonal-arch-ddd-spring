package com.hex.arch.greeting.infra.adapter.inbound;

import com.hex.arch.greeting.domain.model.Recipient;
import com.hex.arch.greeting.domain.repository.RecipientRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipients")
public class RecipientsController {
    private final RecipientRepository recipientRepository;

    public RecipientsController(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @PostMapping
    public ResponseEntity<Recipient> createRecipient(@RequestBody CreateRecipientRequest request) {
        Recipient recipient = new Recipient(
                UUID.randomUUID(),
                request.firstName(),
                request.lastName(),
                Instant.now(),
                request.address(),
                request.gender(),
                request.age()
        );

        recipientRepository.save(recipient);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipient> getRecipient(@PathVariable UUID id) {
        return recipientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable UUID id) {
        recipientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    record CreateRecipientRequest(
            String firstName,
            String lastName,
            String address,
            String gender,
            Integer age
    ) {}
}