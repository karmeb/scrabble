package com.karme.scrabblebackend.service;

import com.karme.scrabblebackend.model.ScrabbleWord;
import com.karme.scrabblebackend.model.ScrabbleWordDto;
import com.karme.scrabblebackend.model.ScrabbleWordInfoDto;
import com.karme.scrabblebackend.repository.ScrabbleWordRepository;
import com.karme.scrabblebackend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScrabbleWordService {

    private final ScrabbleWordRepository scrabbleWordsRepository;
    private final PointsCalculationService pointsCalculationService;

    public ScrabbleWordInfoDto getWordInfo(String word) {
        Boolean isValidWord = scrabbleWordsRepository.existsByWord(word.toLowerCase());

        return ScrabbleWordInfoDto.builder()
                .word(word)
                .isValid(isValidWord)
                .points(isValidWord? pointsCalculationService.calculateScrabbleWordPoints(word): null)
                .build();
    }

    public void addWord(ScrabbleWordDto scrabbleWordDto) {
        String newWord = scrabbleWordDto.getWord().toLowerCase();
        if (scrabbleWordsRepository.existsByWord(newWord)) {
            log.warn("User attempted to add a word '{}' which already exists in db", newWord);
            throw new RuntimeException("Word '" + newWord + "' already exists");
        }
        ScrabbleWord scrabbleWord = new ScrabbleWord();
        scrabbleWord.setWord(newWord);
        scrabbleWordsRepository.save(scrabbleWord);
    }


    public void saveWordsFromFile(String filePath) {
        try {
            List<String> words = FileUtil.readLinesFromFile(filePath);
            for (String word : words) {
                if (!scrabbleWordsRepository.existsByWord(word)) {
                    ScrabbleWord scrabbleWord = new ScrabbleWord();
                    scrabbleWord.setWord(word.toLowerCase());
                    scrabbleWordsRepository.save(scrabbleWord);
                }
            }
        } catch(Exception e) {
            log.error("Error reading words from file, {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
