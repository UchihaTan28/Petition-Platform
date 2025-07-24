import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PetitionService {
  private petitionUrl = `${environment.apiBaseUrl}/petitions`;

  constructor(private http: HttpClient) {}

  getAllPetitions(): Observable<any[]> {
    return this.http.get<any[]>(this.petitionUrl).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Error fetching petitions:', error);

        if (error.error instanceof ErrorEvent) {
          // Client-side error
          return throwError(() => new Error(`Client-side error: ${error.error.message}`));
        } else {
          // Server-side error
          return throwError(() => new Error(`Server error: ${error.status} - ${error.message}`));
        }
      })
    );
  }

  createPetition(email: string, password: string, petitionData: any): Observable<any> {
    // Suppose your endpoint is /api/petitions/create?email=xxx&password=yyy
    const params = `?email=${email}&password=${password}`;
    return this.http.post(`${this.petitionUrl}/create${params}`, petitionData);
  }

  // or if endpoint is just POST /api/petitions
  simpleCreatePetition(petition: any): Observable<any> {
    return this.http.post(this.petitionUrl, petition);
  }

  // For "View"
  getPetitionById(id: number): Observable<any> {
    return this.http.get(`${this.petitionUrl}/${id}`);
  }
}
