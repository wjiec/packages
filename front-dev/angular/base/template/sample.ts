import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ title }}</h1>
    <p>The sum of 1 + 1 is {{ 1 + 1 }}</p>
    <p>Current timestamp is {{ current_time }}</p>
  `
})

export class AppComponent {
  title = 'Template Syntax';

  current_time: Number;

  constructor() {
    setInterval(() => {
      this.current_time = Math.floor(new Date().getTime() / 1000);
    }, 1000);
  }
}
