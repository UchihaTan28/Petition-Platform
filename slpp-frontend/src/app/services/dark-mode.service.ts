import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DarkModeService {
  private darkModeKey = 'darkMode';

  constructor() {
    this.initializeTheme();
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }

  private initializeTheme(): void {
    if (this.isBrowser()) {
      const isDarkMode = localStorage.getItem(this.darkModeKey) === 'true';
      this.applyTheme(isDarkMode);
    }
  }

  toggleDarkMode(): void {
    if (this.isBrowser()) {
      const isDarkMode = document.body.classList.toggle('dark-mode');
      localStorage.setItem(this.darkModeKey, isDarkMode.toString());
    }
  }

  isDarkModeEnabled(): boolean {
    if (this.isBrowser()) {
      return document.body.classList.contains('dark-mode');
    }
    return false;
  }

  private applyTheme(isDarkMode: boolean): void {
    if (isDarkMode) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }
  }
}
