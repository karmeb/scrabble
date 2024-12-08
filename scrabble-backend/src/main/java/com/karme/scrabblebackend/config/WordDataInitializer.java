package com.karme.scrabblebackend.config;

import com.karme.scrabblebackend.service.ScrabbleWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WordDataInitializer implements ApplicationRunner {
    private final ScrabbleWordService scrabbleWordService;

    @Value("${file.words.path}")
    private String filePath;

    @Override
    public void run(ApplicationArguments args) {
        scrabbleWordService.saveWordsFromFile(filePath);
    }
}

