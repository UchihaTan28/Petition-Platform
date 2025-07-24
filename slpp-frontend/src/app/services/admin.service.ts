import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private adminUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  // Set signature threshold
  setSignatureThreshold(threshold: number): Observable<string> {
    return this.http.post<string>(`${this.adminUrl}/setThreshold?threshold=${threshold}`, {}, { headers: this.getAuthHeaders() });
  }

  getSignatureThreshold(): Observable<number> {
    return this.http.get<number>(`${this.adminUrl}/getThreshold`, { headers: this.getAuthHeaders() });
  }

  // Evaluate a petition
  evaluatePetition(petitionId: number): Observable<any> {
    return this.http.post<any>(`${this.adminUrl}/evaluate?petitionId=${petitionId}`, {}, { headers: this.getAuthHeaders() });
  }

  // Close a petition with a response
  closePetition(petitionId: number, response: string): Observable<any> {
    return this.http.post<any>(`${this.adminUrl}/close?petitionId=${petitionId}`, response, {
      headers: this.getAuthHeaders().set('Content-Type', 'text/plain')
    });
  }

  
  /*closePetition(petitionId: number, response: string): Observable<any> {
    return this.http.post<any>(`${this.adminUrl}/close?petitionId=${petitionId}`, response, {
      headers: { 'Content-Type': 'text/plain' }
    });
  }*/
}
