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
        Optional<ScrabbleWord> scrabbleWordOptional = scrabbleWordsRepository.findByWord(word.toUpperCase());
        Boolean isValidWord = scrabbleWordOptional.isPresent();

        return ScrabbleWordInfoDto.builder()
                .word(word)
                .isValid(isValidWord)
                .points(isValidWord? pointsCalculationService.calculateScrabbleWordPoints(word): null)
                .build();
    }


    public void saveWordsFromFile(String filePath) {
        List<String> words = FileUtil.readLinesFromFile(filePath);

        for (String word : words) {
            if (!scrabbleWordsRepository.existsByWord(word)) {
                ScrabbleWord scrabbleWord = new ScrabbleWord();
                scrabbleWord.setWord(word);
                scrabbleWordsRepository.save(scrabbleWord);
            }
        }
    }

    public void addWord(ScrabbleWordDto scrabbleWordInfoDto) {
        String newWord = scrabbleWordInfoDto.getWord().toUpperCase();
        if (scrabbleWordsRepository.existsByWord(newWord)) {
            log.error("Word {} already exists", newWord);
            throw new RuntimeException("Word already exists");
        }
        ScrabbleWord scrabbleWord = new ScrabbleWord();
        scrabbleWord.setWord(newWord);
        scrabbleWordsRepository.save(scrabbleWord);
    }
}
