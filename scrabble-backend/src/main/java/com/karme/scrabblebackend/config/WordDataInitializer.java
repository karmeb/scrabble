package com.karme.scrabblebackend.config;

import com.karme.scrabblebackend.service.ScrabbleWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WordDataInitializer implements CommandLineRunner {
    private final ScrabbleWordService scrabbleWordService;

    @Value("${file.words.path}")
    private String filePath;

    @Override
    public void run(String... args) {
        scrabbleWordService.saveWordsFromFile(filePath);
    }
}

