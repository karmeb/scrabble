package com.karme.scrabblebackend.repository;

import com.karme.scrabblebackend.model.ScrabbleWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScrabbleWordRepository extends JpaRepository<ScrabbleWord, Long> {

    Optional<ScrabbleWord> findByWord(String word);

    boolean existsByWord(String word);

}
