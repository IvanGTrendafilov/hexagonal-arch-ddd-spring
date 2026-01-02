package com.hex.arch.greeting.client.driving.port;

import com.hex.arch.greeting.client.CreateGreetingRequest;
import com.hex.arch.greeting.client.GreetingResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@NullMarked
@RequestMapping("/api/v1/greetings")
public interface GreetingsAPI {


    // TODO - Move this driving port as part of the domain/server

    @PostMapping
    ResponseEntity<GreetingResponse> createGreeting(@Valid @RequestBody CreateGreetingRequest request);

    @GetMapping("/{id}")
    ResponseEntity<GreetingResponse> getGreeting(@PathVariable UUID id);

    @DeleteMapping("/{id}")
    void deleteGreeting(@PathVariable UUID id);
}

