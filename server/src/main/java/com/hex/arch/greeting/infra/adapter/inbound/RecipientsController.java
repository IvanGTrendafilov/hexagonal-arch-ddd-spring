package com.hex.arch.greeting.infra.adapter.inbound;

import com.hex.arch.greeting.client.CreateRecipientRequest;
import com.hex.arch.greeting.client.RecipientResponse;
import com.hex.arch.greeting.client.driving.port.RecipientsAPI;
import com.hex.arch.greeting.domain.model.Recipient;
import com.hex.arch.greeting.domain.repository.RecipientRepository;
import jakarta.validation.Valid;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipients")
public class RecipientsController implements RecipientsAPI {
    private final RecipientRepository recipientRepository;

    public RecipientsController(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @Override
    public ResponseEntity<RecipientResponse> createRecipient(@Valid @RequestBody CreateRecipientRequest request) {
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
        RecipientResponse response = toResponse(recipient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<RecipientResponse> getRecipient(@PathVariable UUID id) {
        return recipientRepository.findById(id)
                .map(recipient -> ResponseEntity.ok(toResponse(recipient)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public void deleteRecipient(@PathVariable UUID id) {
        recipientRepository.deleteById(id);
    }

    private RecipientResponse toResponse(Recipient recipient) {
        return new RecipientResponse(
                recipient.id(),
                recipient.firstName(),
                recipient.lastName(),
                recipient.createdAt(),
                recipient.address(),
                recipient.gender(),
                recipient.age()
        );
    }
}