package com.karme.scrabblebackend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrabbleWordDto {
    @NotBlank(message = "Word must not be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Word must contain only letters")
    private String word;
}
