package com.karme.scrabblebackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ScrabbleWordInfoDto {
    private String word;
    private Boolean isValid;
    private Integer points;
}
