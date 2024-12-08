import { Component, inject, OnInit } from '@angular/core';
import { ScrabbleWordsService } from '../../services/scrabble-words.service';

@Component({
  selector: 'app-add-word',
  imports: [],
  templateUrl: './add-word.component.html',
  styleUrl: './add-word.component.scss'
})
export class AddWordComponent implements OnInit {
  scrabbleWordsService = inject(ScrabbleWordsService);

  ngOnInit(): void {
    console.log("oninit")
  }
}
