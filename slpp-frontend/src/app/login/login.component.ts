import { Component, importProvidersFrom, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../services/user.service';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { withFetch } from '@angular/common/http';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    providers: [
      
    ],
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  credentials = { email: '', password: '' };
  isDarkMode = false;
  constructor(
    private userService: UserService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    //Auto-fill if saved in localStorage
    const lastLoginEmail = localStorage.getItem('lastLoginEmail');
    if (lastLoginEmail) {
      this.credentials.email = lastLoginEmail;
    }
    this.isDarkMode = localStorage.getItem('darkMode') === 'true';
    this.updateTheme();

  }

  toggleDarkMode(): void {
    this.isDarkMode = !this.isDarkMode;
    localStorage.setItem('darkMode', this.isDarkMode.toString());
    this.updateTheme();
  }

  private updateTheme(): void {
    if (this.isDarkMode) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }
  }

  onSubmit() {
    this.userService.login(this.credentials).subscribe({
      next: (res) => {
        console.log('Login response:', res);
        if (res && res.id) {
          this.authService.setUserData({ id: res.id, fullName: res.fullName, role: res.role });

          // Save email for next login
          localStorage.setItem('lastLoginEmail', this.credentials.email);

          if (res.role === 'ADMIN') {
            this.router.navigate(['/admin-dashboard']);
          } else {
            this.router.navigate(['/dashboard']);
          }
        }
      },
      error: (err) => {
        console.error('Login error', err);
        alert('Invalid email or password');
      }
    });
  }
}
