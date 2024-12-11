package com.karme.scrabblebackend.service;

import com.karme.scrabblebackend.model.ScrabbleWord;
import com.karme.scrabblebackend.model.ScrabbleWordDto;
import com.karme.scrabblebackend.repository.ScrabbleWordRepository;
import com.karme.scrabblebackend.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ScrabbleWordServiceIntegrationTest {

    @Autowired
    private ScrabbleWordRepository scrabbleWordsRepository;

    @Mock
    private PointsCalculationService pointsCalculationService;

    private ScrabbleWordService scrabbleWordService;

    @BeforeEach
    public void setUp() {
        scrabbleWordService = new ScrabbleWordService(scrabbleWordsRepository, pointsCalculationService);
    }

    @Test
    public void addWord_savesWord_whenWordDoesNotExist() {
        String newWord = "newword";
        ScrabbleWordDto scrabbleWordDto = new ScrabbleWordDto();
        scrabbleWordDto.setWord(newWord);

        scrabbleWordService.addWord(scrabbleWordDto);

        ScrabbleWord savedWord = scrabbleWordsRepository.findByWord(newWord).orElse(null);
        assertNotNull(savedWord);
        assertEquals(newWord, savedWord.getWord());
    }

    @Test
    void saveWordsFromFile_savesWordsFromFileToDatabase() {

        String filePath = "src/test/resources/test-dictionary.txt";

        scrabbleWordService.saveWordsFromFile(filePath);

        assertTrue(scrabbleWordsRepository.existsByWord("firstword"));
        assertTrue(scrabbleWordsRepository.existsByWord("secondword"));
        assertTrue(scrabbleWordsRepository.existsByWord("thirdword"));

    }

    @Test
    void saveWordsFromFile_doesNotAddDuplicateWords() {
        String filePath = "src/test/resources/test-dictionary.txt";

        MockedStatic<FileUtil> mockedFileUtil = mockStatic(FileUtil.class);
        when(FileUtil.readLinesFromFile(filePath)).thenReturn(Arrays.asList("firstword", "secondword", "thirdword", "firstword"));

        scrabbleWordService.saveWordsFromFile(filePath);

        assertEquals(3,scrabbleWordsRepository.count());

        mockedFileUtil.close();
    }
}
