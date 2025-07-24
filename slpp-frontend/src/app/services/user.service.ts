import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class UserService {
  private userUrl = `${environment.apiBaseUrl}/users`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  register(userData: any): Observable<any> {
    return this.http.post(`${this.userUrl}/register`, userData);
  }

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.userUrl}/login`, credentials).pipe(
      tap((response) => {
        if (response && response.token) {
          this.authService.setToken(response.token);  // Save JWT token
        }
      })
    );
  }
}
