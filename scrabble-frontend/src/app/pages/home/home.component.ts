import { Component, inject, signal } from '@angular/core';
import { ScrabbleWordsService } from '../../services/scrabble-words.service';
import { catchError } from 'rxjs';
import { ScrabbleWord } from '../../model/scrabble-word.type';
import { FormControl, FormGroup, MaxLengthValidator, ReactiveFormsModule, Validators } from '@angular/forms';
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
  errorMessage = signal<string | null>(null);

  applyForm = new FormGroup({
    word: new FormControl('', [Validators.required, Validators.maxLength(100)])
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
          if (error.status === 400) {
            const errorString = Object.values(error.error)
              .map((message) => `${message}`)
              .join('\n');
            this.errorMessage.set(errorString);
          } else {
            this.errorMessage.set('An error occurred while fetching word info.');
          }
          throw error;
        })
      ).subscribe((wordInfo: ScrabbleWord) => {
        this.errorMessage.set(null);
        this.scrabbleWord.set(wordInfo);
      })
  }

  get word() {
    return this.applyForm.get('word') as FormControl;
  }

  closeErrorNotification() {
    this.errorMessage.set(null);
  }
}
