import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class SignatureService {
  private signaturesUrl = `${environment.apiBaseUrl}/signatures`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  signPetition(petitionId: number, userId: number): Observable<any> {
    return this.http.post<any>(this.signaturesUrl, {
      petitionId,
      userId
    }, {
        headers: { 'Content-Type': 'application/json' },
        responseType: 'json'  // Ensure response is treated as JSON
      });
  }
    /*signPetition(petitionId: number, userId: number) {
      return this.http.post(`/api/signatures`, { petitionId, userId }, {
        headers: { 'Content-Type': 'application/json' },
        responseType: 'json'  // Ensure response is treated as JSON
      });
    }*/
}
