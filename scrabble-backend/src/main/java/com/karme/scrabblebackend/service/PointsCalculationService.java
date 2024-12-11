package com.karme.scrabblebackend.service;

import com.karme.scrabblebackend.model.ScrabbleLetter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PointsCalculationService {
    public Integer calculateScrabbleWordPoints(String word) {
        if (word == null || word.isBlank()) {
            return null;
        }
        Integer totalPoints = 0;
        for (Character letter : word.toCharArray()) {
            totalPoints += getPointsForLetter(letter);
        }
        log.info("Calculated points for word '{}', total points: {}", word, totalPoints);
        return totalPoints;
    }

    private Integer getPointsForLetter(Character character) {
        try {
            return ScrabbleLetter.valueOf(character.toString().toUpperCase()).getPoints();
        } catch (Exception e) {
            log.error("Unable to get points for character '{}', due to error: ", character, e);
            throw new RuntimeException("Unable to get points for character: " + character, e);
        }
    }
}
