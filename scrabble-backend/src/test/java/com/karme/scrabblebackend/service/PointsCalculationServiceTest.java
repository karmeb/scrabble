package com.karme.scrabblebackend.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointsCalculationServiceTest {

    private final PointsCalculationService pointsCalculationService = new PointsCalculationService();

    @Test
    void calculateScrabbleWordPoints_ReturnsNull_WhenInputIsNullOrBlank() {
        Integer resultNull = pointsCalculationService.calculateScrabbleWordPoints(null);
        Integer resultBlank = pointsCalculationService.calculateScrabbleWordPoints(" ");

        assertNull(resultNull, "Expected to return null when input word is null");
        assertNull(resultBlank, "Expected to return null when input word is a blank string");
    }


    @Test
    void calculateScrabbleWordPoints_ReturnsPoints_WhenInputIsWordString() {
        Integer result = pointsCalculationService.calculateScrabbleWordPoints("EAIONRTLSUDGBCMPFHVWYKJXQZ");

        assertEquals(87, result);
    }

    @Test
    void calculateScrabbleWordPoints_ShouldThrowException_ForInvalidCharacter() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pointsCalculationService.calculateScrabbleWordPoints("invalid-word");
        });
        assertEquals("Unable to get points for character: -", exception.getMessage());
    }
}