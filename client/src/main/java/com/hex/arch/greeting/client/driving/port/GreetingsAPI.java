package com.hex.arch.greeting.client.driving.port;

import com.hex.arch.greeting.client.CreateGreetingRequest;
import com.hex.arch.greeting.client.GreetingResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NullMarked
@RequestMapping("/api/v1/greetings")
public interface GreetingsAPI {     //driving port

    @PostMapping
    ResponseEntity<GreetingResponse> createGreeting(@Valid @RequestBody CreateGreetingRequest request);

    @GetMapping
    ResponseEntity<List<GreetingResponse>> getAllGreetings();

    @GetMapping("/{id}")
    ResponseEntity<GreetingResponse> getGreeting(@PathVariable UUID id);

    @DeleteMapping("/{id}")
    void deleteGreeting(@PathVariable UUID id);
}