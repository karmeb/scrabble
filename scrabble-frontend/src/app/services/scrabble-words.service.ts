import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { ScrabbleWord } from '../model/scrabble-word.type';

@Injectable({
  providedIn: 'root'
})
export class ScrabbleWordsService {
  http = inject(HttpClient);

  getWordInfoFromApi(word: string) {
    const url = `http://localhost:8080/api/words/${word}/info`;
    return this.http.get<ScrabbleWord>(url);
  }

  postNewWord(newWord: string) {
    const url = `http://localhost:8080/api/words/add`;
    return this.http.post(url, { "word": newWord });
  }
}
