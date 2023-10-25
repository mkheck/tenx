package com.thehecklers.tenx;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class DataLoader {
    public DataLoader(PetRepository repo) {
        Stream.of(new Pet("dog"),
                new Pet("cat"),
                new Pet("tribble"))
                .forEach(repo::save);
    }
}
