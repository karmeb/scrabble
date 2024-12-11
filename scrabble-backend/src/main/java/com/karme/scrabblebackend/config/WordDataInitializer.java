package com.karme.scrabblebackend.config;

import com.karme.scrabblebackend.service.ScrabbleWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class WordDataInitializer implements ApplicationRunner {
    private final ScrabbleWordService scrabbleWordService;

    @Value("${file.words.path:#{null}}")
    private String filePath;

    @Override
    public void run(ApplicationArguments args) {

        if (filePath == null) {
            throw new RuntimeException("No initial words list file path specified");
        }

        log.info("Initializing word data, using words file path: {}", filePath);
        scrabbleWordService.saveWordsFromFile(filePath);
        log.info("Initializing word data completed");
    }
}

