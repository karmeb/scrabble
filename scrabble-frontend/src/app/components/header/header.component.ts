import { Component, OnInit, signal, inject } from '@angular/core';
import { Router, RouterLink, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  router = inject(Router)
  title = signal('Scrabble Words Helper')
  routPath = signal<string>('')

  navigationButtonText: { [key: string]: string } = {
    '/': 'Check Words',
    '/add-word': 'Add New Word',
  };

  ngOnInit() {
    this.router.events.subscribe(
      (event: any) => {
        if (event instanceof NavigationEnd) {
          let currentUrl = this.router.url;
          if (currentUrl === "/") {
            this.routPath.set("/add-word")
          } else {
            this.routPath.set("/")
          }
        }
      }
    );
  }

}
