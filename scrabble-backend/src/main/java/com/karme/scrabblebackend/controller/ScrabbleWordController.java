package com.karme.scrabblebackend.controller;

import com.karme.scrabblebackend.model.ScrabbleWordDto;
import com.karme.scrabblebackend.model.ScrabbleWordInfoDto;
import com.karme.scrabblebackend.service.ScrabbleWordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/words")
@RequiredArgsConstructor
@Log4j2
public class ScrabbleWordController {

    private final ScrabbleWordService scrabbleWordService;

    @GetMapping("/{word}/info")
    public ScrabbleWordInfoDto getWordInfo(@PathVariable String word) {
        log.info("Get word info request received for word: {}", word);
        return scrabbleWordService.getWordInfo(word);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewWord(@Valid @RequestBody ScrabbleWordDto scrabbleWordDto) {
        log.info("request to add new word received for word: {}", scrabbleWordDto.getWord());
        scrabbleWordService.addWord(scrabbleWordDto);
    }

}
