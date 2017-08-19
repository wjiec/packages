import { Component } from '@angular/core';

interface ChangeHistory {
    old_value: string;
    new_value: string;
}

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
                <p><span>{{ hero.name }}</span><input [(ngModel)]="hero.name" #hero_input><span>{{ hero_input.value }}</span></p>
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
        <form #heroForm="ngForm" (ngSubmit)="addNewHero(heroForm)">
            Name: <input [(ngModel)]="new_hero.name" name="name"><br>
            Submit: <button>Add new</button>
        </form>

        <h2>Binding Syntax</h2>
        <h3>From the source-to-view</h3>
        <p>Interpolation -> {{ current_time }}</p>
        <p bind-id="'id_' + current_time" #attr_p>Property -> {{ attr_p.id }}</p>
        <h3>From view-to-source(Event)</h3>
        ChangeEvent: <input #changeEl on-change="changeHistory(changeEl)"><br>
        ChangeHistory:
        <ul>
            <li *ngFor="let h of change_history">{{ h.old_value }} -> {{ h.new_value }}</li>
        </ul>
        <h3>View-to-Source-to-View</h3>
        Self description:
        <input [(ngModel)]="self_id" />
        <p>This is my id: {{ self_id }}</p>
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

    new_hero = { name: '' };

    change_history: Array<ChangeHistory> = [];

    self_id = '';

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
        this.hero_array.push(form.value);
    }

    changeHistory(event) {
        let old_value = '';
        if (this.change_history.length !== 0) {
            old_value = this.change_history[this.change_history.length - 1].new_value;
        }

        this.change_history.push({
            old_value: old_value,
            new_value: event.value
        });
    }
}
