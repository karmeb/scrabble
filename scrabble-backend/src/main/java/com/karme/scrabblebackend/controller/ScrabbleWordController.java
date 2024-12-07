package com.karme.scrabblebackend.controller;

import com.karme.scrabblebackend.model.ScrabbleWordDto;
import com.karme.scrabblebackend.model.ScrabbleWordInfoDto;
import com.karme.scrabblebackend.service.ScrabbleWordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public void addNewWord(@Valid @RequestBody ScrabbleWordDto scrabbleWordDto) {
        log.info("request to add new word received for word: {}", scrabbleWordDto.getWord());
        scrabbleWordService.addWord(scrabbleWordDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleInternalServerError(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal server error");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
