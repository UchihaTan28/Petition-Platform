import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DarkModeService } from './services/dark-mode.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  isDarkMode = false;

  constructor(private darkModeService: DarkModeService) {}

  ngOnInit(): void {
    this.isDarkMode = this.darkModeService.isDarkModeEnabled();
  }

  toggleDarkMode(): void {
    this.darkModeService.toggleDarkMode();
    this.isDarkMode = this.darkModeService.isDarkModeEnabled();
  }
  title = 'slpp-frontend';
}
