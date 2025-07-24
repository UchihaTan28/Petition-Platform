import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../services/user.service';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router'; 
import { ZXingScannerModule } from '@zxing/ngx-scanner';

@Component({
  selector: 'app-register',
  imports: [FormsModule, CommonModule, RouterModule, ZXingScannerModule],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  user = {
    email: '',
    fullName: '',
    dateOfBirth: '',
    password: '',
    biometricId: ''
  };
  showScanner= false;

  constructor(private userService: UserService, private router: Router) {}

  isSubmitting = false;

  // Handle QR scan result
  onCodeResult(result: string) {
    this.user.biometricId = result;
    this.showScanner = false;  // Close scanner after successful scan
  }

   // Toggle QR Scanner visibility
   toggleScanner() {
    this.showScanner = !this.showScanner;
  }

onSubmit() {
  this.isSubmitting = true;
  this.userService.register(this.user).subscribe({
    next: (response) => {
      this.isSubmitting = false;
      alert('Registration successful! Please log in.');
      this.router.navigate(['/login']);
    },
    error: (err) => {
      this.isSubmitting = false;
      alert('Registration failed. Possible reason: wrong BioID.'/* || err.error?.message*/);
    }
  });
}
}
