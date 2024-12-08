import { Component, inject, signal } from '@angular/core';
import { ScrabbleWordsService } from '../../services/scrabble-words.service';
import { catchError } from 'rxjs';
import { ScrabbleWord } from '../../model/scrabble-word.type';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UpperCasePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [ReactiveFormsModule, UpperCasePipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  scrabbleWordsService = inject(ScrabbleWordsService);
  scrabbleWord = signal<ScrabbleWord | null>(null);
  searchWord: string = '';

  applyForm = new FormGroup({
    word: new FormControl('', [Validators.required])
  });

  onSubmit() {
    if (this.applyForm.invalid) {
      this.applyForm.markAllAsTouched();
      return;
    }

    this.getScrabbleWordInfo(this.applyForm.value.word ?? '');
  }

  getScrabbleWordInfo(word: string): void {
    this.scrabbleWordsService.getWordInfoFromApi(word)
      .pipe(
        catchError((error) => {
          console.log(error);
          throw error;
        })
      ).subscribe((wordInfo: ScrabbleWord) => {
        this.scrabbleWord.set(wordInfo);
      })
  }

  get word() {
    return this.applyForm.get('word') as FormControl;
  }
}
