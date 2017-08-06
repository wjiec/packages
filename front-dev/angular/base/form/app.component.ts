import { Component } from '@angular/core';

class Hero {
  constructor(
    public id: number,
    public name: string,
    public power: string,
    public age?: number
  ) {}
}

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ title }}</h1>
    <div id="hero-form" [hidden]="submitted">
      <form #heroForm="ngForm">
        <div class="input-group">
          <label for="hero-name">Hero Name</label>
          <input id="hero-name" name="name" [(ngModel)]="hero.name" required>
        </div>

        <div class="input-group">
          <label for="hero-power">Hero Power</label>
          <select name="power" id="hero-power" [(ngModel)]="hero.power" required>
            <option *ngFor="let power of powers" (value)="power">{{ power }}</option>
          </select>
        </div>

        <div class="input-group">
          <label for="hero-age">Hero Age</label>
          <input type="number" name="age" id="hero-age" [(ngModel)]="hero.age">
        </div>

        <button (click)="new_hero(heroForm);" [disabled]="!heroForm.form.valid">New Hero</button>
      </form>
    </div>
    <div id="hero-info" [hidden]="!submitted">
      <h2>You submitted the following:</h2>
      <div>
        <span>Name</span>
        <span>{{ hero.name }}</span>
      </div>
      <div>
        <span>Power</span>
        <span>{{ hero.power }}</span>
      </div>
      <div class="row">
        <span>Age</span>
        <span>{{ hero.age }}</span>
      </div>
      <br>
      <button (click)="submitted = false">Edit</button>
    </div>
  `,
  styleUrls: ['./app.style.css']
})

export class AppComponent {
  title = 'Hero Employment Agency';
  powers = [
    'Really Smart', 'Super Flexible', 'Super Hot', 'Weather Changer'
  ];
  hero = new Hero(1, 'WindStorm', 'Weather Changer');

  submitted = false;

  new_hero(form: any) {
    this.submitted = true;
  }
}
