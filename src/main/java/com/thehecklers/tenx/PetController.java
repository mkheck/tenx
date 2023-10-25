package com.thehecklers.tenx;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {
    private final PetRepository repo;

    @Value("${spring.message:Message missing}")
    private String message;

    public PetController(PetRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String greetings() {
        return "Let's IGNITE our Java development Process!";
    }

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }

    @GetMapping("/pets")
    public Iterable<Pet> getPets() {
        return repo.findAll();
    }

    // Get a pet by its id using @PathVariable
    @GetMapping("/pets/{id}")
    public Optional<Pet> getPetById(@PathVariable Long id) {
        return repo.findById(id);
    }
}
