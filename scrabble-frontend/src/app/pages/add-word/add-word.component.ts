import { Component, inject } from '@angular/core';
import { ScrabbleWordsService } from '../../services/scrabble-words.service';
import { catchError } from 'rxjs';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-word',
  imports: [ReactiveFormsModule],
  templateUrl: './add-word.component.html',
  styleUrl: './add-word.component.scss'
})
export class AddWordComponent {
  toasterService = inject(ToastrService);
  scrabbleWordsService = inject(ScrabbleWordsService);

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
            this.showErrorToaster(errorString);
          } else {
            this.showErrorToaster(error.error.message || "Unable to add the word, please try again later.");
          }
          throw error;
        })
      ).subscribe(() => {
        this.showSuccessToaster(`Word '${word.toUpperCase()}' added successfully`)
      })
  }

  get word() {
    return this.applyForm.get('word') as FormControl;
  }

  showErrorToaster(message: string) {
    this.toasterService.error(message, 'Error:',);
  }

  showSuccessToaster(message: string) {
    this.toasterService.success(message, 'Success:',);
  }
}
