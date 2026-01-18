package com.hex.arch.greeting.infra.adapter.inbound;

import com.hex.arch.greeting.client.CreateGreetingRequest;
import com.hex.arch.greeting.client.GreetingResponse;
import com.hex.arch.greeting.client.GreetingResponse.RecipientResponse;
import com.hex.arch.greeting.client.driving.port.GreetingsAPI;
import com.hex.arch.greeting.domain.model.Greeting;
import com.hex.arch.greeting.domain.service.GreetingService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NullMarked
@RestController
@RequestMapping("/api/v1/greetings")
public class ForGreetingsController implements GreetingsAPI {        // implements our driving port, handles http request, converts
                                                                     //  to and from JSON
    private static final Logger log = LoggerFactory.getLogger(ForGreetingsController.class);
    private final GreetingService greetingService;

    public ForGreetingsController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public ResponseEntity<GreetingResponse> createGreeting(@Valid @RequestBody CreateGreetingRequest request) {
        log
                .atInfo()
                .addKeyValue("message", request.message())
                .addKeyValue("recipientId", request.recipientId())
                .log("Creating greeting");

        Greeting greeting = greetingService.createGreeting(request.message(), request.recipientId());
        GreetingResponse response = toResponse(greeting);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<GreetingResponse> getGreeting(@PathVariable UUID id) {
        log.atInfo().addKeyValue("id", id).log("Finding greeting");

        return greetingService
                .getGreeting(id)
                .map(greeting -> ResponseEntity.ok(toResponse(greeting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void deleteGreeting(@PathVariable UUID id) {
        log.atInfo().addKeyValue("id", id).log("Deleting greeting");
        greetingService.deleteGreeting(id);
    }

    @GetMapping
    public ResponseEntity<List<GreetingResponse>> getAllGreetings() {
        log.atInfo().log("Getting all greetings");

        List<GreetingResponse> responses = greetingService.getAllGreetings()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    private GreetingResponse toResponse(Greeting greeting) {
        RecipientResponse recipientResponse = null;
        if (greeting.recipient() != null) {
            var r = greeting.recipient();
            recipientResponse = new RecipientResponse(
                    r.id(),
                    r.firstName(),
                    r.lastName(),
                    r.address(),
                    r.gender(),
                    r.age()
            );
        }

        return new GreetingResponse(
                greeting.id(),
                greeting.message(),
                recipientResponse,
                greeting.createdAt()
        );
    }
}