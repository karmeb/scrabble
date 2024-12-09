import { Component, inject, signal } from '@angular/core';
import { ScrabbleWordsService } from '../../services/scrabble-words.service';
import { catchError } from 'rxjs';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-word',
  imports: [ReactiveFormsModule],
  templateUrl: './add-word.component.html',
  styleUrl: './add-word.component.scss'
})
export class AddWordComponent {
  scrabbleWordsService = inject(ScrabbleWordsService);
  errorMessage = signal<string | null>(null);

  applyForm = new FormGroup({
    word: new FormControl('', [Validators.required, Validators.maxLength(100)])
  });

  onSubmit() {
    if (this.applyForm.invalid) {
      this.applyForm.markAllAsTouched();
      return;
    }

    this.submitNewWord(this.applyForm.value.word ?? '');
  }

  submitNewWord(word: string): void {
    this.scrabbleWordsService.postNewWord(word)
      .pipe(
        catchError((error) => {
          if (error.status === 400) {
            const errorString = Object.values(error.error)
              .map((message) => `${message}`)
              .join('\n');
            this.errorMessage.set(errorString);
          } else {
            this.errorMessage.set(error.error.message);
          }
          throw error;
        })
      ).subscribe(() => {
        this.errorMessage.set(null);
      })
  }

  get word() {
    return this.applyForm.get('word') as FormControl;
  }

  closeErrorNotification() {
    this.errorMessage.set(null);
  }
}
