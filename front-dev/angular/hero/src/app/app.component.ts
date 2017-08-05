import { Component } from '@angular/core';
import { Hero } from './hero';

@Component({
  selector: 'app-root',
  templateUrl: './app.template.html'
})

export class AppComponent {
  title: string;
  heroes: Array<Hero>;

  constructor() {
    this.title = 'Hero Manager';
    this.heroes = [
      new Hero(1, 'A'),
      new Hero(2, 'B')
    ];

    setInterval(() => {
      this.heroes.push(
        new Hero(this.heroes[this.heroes.length - 1].id + 1,
        'ABCDEFGHJKMNPQRSTWXYZ'.charAt(Math.floor(Math.random() * 25)))
      );
    }, 1000);
  }
}
