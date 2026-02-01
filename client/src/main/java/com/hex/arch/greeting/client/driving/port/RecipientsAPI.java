package com.hex.arch.greeting.client.driving.port;

import com.hex.arch.greeting.client.CreateRecipientRequest;
import com.hex.arch.greeting.client.RecipientResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NullMarked
@RequestMapping("/api/v1/recipients")
public interface RecipientsAPI {

    @PostMapping
    ResponseEntity<RecipientResponse> createRecipient(@Valid @RequestBody CreateRecipientRequest request);

    @GetMapping("/{id}")
    ResponseEntity<RecipientResponse> getRecipient(@PathVariable UUID id);

    @DeleteMapping("/{id}")
    void deleteRecipient(@PathVariable UUID id);
}