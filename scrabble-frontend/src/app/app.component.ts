import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent],
  template: `
  <app-header />
  <main class="section">
    <router-outlet />
  </main>
  `,
  styles: [
    `
    main {
      position: absolute;
      top: 20%;
      width: 100%;
      height: 70%;
    }
    `
  ]
})
export class AppComponent {
  title = 'scrabble-frontend';
}
