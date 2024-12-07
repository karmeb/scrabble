package com.karme.scrabblebackend.service;

import com.karme.scrabblebackend.model.ScrabbleLetter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PointsCalculationService {
    public Integer calculateScrabbleWordPoints(String word) {
        if (word == null || word.isEmpty()) {
            return null;
        }
        Integer totalPoints = 0;
        for (Character letter : word.toCharArray()) {
            totalPoints += getPointsForLetter(letter);
        }
        log.info("Calculated points for word: {}, total points: {}", word, totalPoints);
        return totalPoints;
    }

    private Integer getPointsForLetter(Character letter) {
        try {
            return ScrabbleLetter.valueOf(letter.toString().toUpperCase()).getPoints();
        } catch (Exception e) {
            log.error("Unable to get points for letter: {}, due to error: ", letter, e);
            throw new RuntimeException("Unable to get points for letter: " + letter, e);
        }
    }
}
