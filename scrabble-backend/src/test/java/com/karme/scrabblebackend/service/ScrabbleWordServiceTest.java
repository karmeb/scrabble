package com.karme.scrabblebackend.service;

import com.karme.scrabblebackend.model.ScrabbleWord;
import com.karme.scrabblebackend.model.ScrabbleWordDto;
import com.karme.scrabblebackend.repository.ScrabbleWordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScrabbleWordServiceTest {

    @Mock
    private ScrabbleWordRepository scrabbleWordsRepository;

    @Mock
    private PointsCalculationService pointsCalculationService;

    @InjectMocks
    private ScrabbleWordService scrabbleWordService;

    @Test
    void getWordInfo_returnsScrabbleWordInfoDto_WhenWordInDatabase() {
        String wordToCheck = "someword";
        Integer points = 14;

        ScrabbleWord scrabbleWord = new ScrabbleWord();
        scrabbleWord.setWord(wordToCheck);
        scrabbleWord.setId(1L);

        when(scrabbleWordsRepository.findByWord(wordToCheck)).thenReturn(Optional.of(scrabbleWord));
        when(pointsCalculationService.calculateScrabbleWordPoints(wordToCheck)).thenReturn(14);

        assertEquals(wordToCheck, scrabbleWordService.getWordInfo(wordToCheck).getWord());
        assertEquals(points, scrabbleWordService.getWordInfo(wordToCheck).getPoints());
        assertTrue(scrabbleWordService.getWordInfo(wordToCheck).getIsValid());
    }

    @Test
    void getWordInfo_returnsScrabbleWordInfoDto_WhenWordNotInDatabase() {
        String wordToCheck = "anotherword";

        when(scrabbleWordsRepository.findByWord(wordToCheck)).thenReturn(Optional.empty());

        assertEquals(wordToCheck, scrabbleWordService.getWordInfo(wordToCheck).getWord());
        assertNull(scrabbleWordService.getWordInfo(wordToCheck).getPoints());
        assertFalse(scrabbleWordService.getWordInfo(wordToCheck).getIsValid());
    }

    @Test
    void addWord_throwsException_whenWordAlreadyExistsInData() {
        String existingWord = "existingword";
        ScrabbleWordDto scrabbleWordDto = new ScrabbleWordDto();
        scrabbleWordDto.setWord(existingWord);

        when(scrabbleWordsRepository.existsByWord(existingWord)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            scrabbleWordService.addWord(scrabbleWordDto);
        });
    }

}