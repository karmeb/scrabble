package com.karme.scrabblebackend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrabbleWordDto {
    @NotBlank(message = "Word must not be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Word must contain only letters")
    @Size(max = 100, message = "Word must not exceed 100 characters")
    private String word;
}
