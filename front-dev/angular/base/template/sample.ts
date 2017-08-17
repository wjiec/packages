import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ title }}</h1>

    <h2>Interpolation</h2>
    <p>Hello, I'm {{ hero_name }}</p>
    <p>Current unix timestamp is {{ current_time }}</p>

    <h2>Template expressions</h2>
    <p>The sum of 1 + 1 is {{ 1 + 1 }}</p>

    <h2>Expression context</h2>
    <p>I have the following heroes</p>
    <ul>
      <li *ngFor="let hero of hero_array">
        <p><span>{{ hero.name }}</span><input [value]="hero.name" #hero_input><span>{{ hero_input.value }}</span></p>
      </li>
    </ul>
    <p>An expression may also refer to properties of the template's context</p>
    <p>template variable name > directive's context > component's member names</p>
    <p>They are restricted to referencing members of the **expression context**</p>

    <h2>Template statements</h2>
    <button (click)="sayHello()">say hello</button>

    <h2>Statement context</h2>
    <button (click)="sayCount()">say heroes count</button>
    <button *ngFor="let hero of hero_array" (click)="heroNameUpper(hero)">{{ hero.name }}</button>
    <!--<form #heroForm *ngSubmit="addNewHero(heroForm)">-->
      <!--Name: <input name="name"><br>-->
      <!--<button>Add new</button>-->
    <!--</form>-->
  `
})

export class AppComponent {
  title = 'Template Syntax';

  hero_name = 'Iron Man';

  current_time: Number;

  hero_array = [
    { name: 'Iron Man' },
    { name: 'Spider Man' }
  ];

  constructor() {
    setInterval(() => {
      this.current_time = Math.floor(new Date().getTime() / 1000);
    }, 1000);
  }

  sayHello() {
    alert(`Hello, I'm Iron Man`);
  }

  sayCount() {
    alert(`I have ${this.hero_array.length} heroes`);
  }

  heroNameUpper(hero) {
    hero.name = hero.name.toUpperCase();
  }

  addNewHero(form) {
    console.log(form);
  }
}
