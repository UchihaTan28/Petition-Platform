import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _userId: number | null = null;
  private _userName: string | null = null;
  private _role: string | undefined;

  private _tokenKey = 'authToken';  // Key to store token in localStorage

  // Save the token to localStorage after login
  setToken(token: string): void {
    localStorage.setItem(this._tokenKey, token);
  }

  // Retrieve the token
  getToken(): string | null {
    return localStorage.getItem(this._tokenKey);
  }

  // Clear token on logout
  clearToken(): void {
    localStorage.removeItem(this._tokenKey);
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }

  /// Getter for User ID with localStorage
  get userId(): number | null {
    return this.isBrowser() ? JSON.parse(localStorage.getItem('userId') || 'null') : null;
  }

  set userId(value: number | null) {
    this._userId = value;
    if (this.isBrowser()) {
      localStorage.setItem('userId', JSON.stringify(value));
    }
  }

  get userName(): string | null {
    return this.isBrowser() ? localStorage.getItem('userName') : null;
  }

  set userName(value: string | null) {
    this._userName = value;
    if (this.isBrowser()) {
      localStorage.setItem('userName', value || '');
    }
  }

  //getter for role
  get role(): string | undefined {
    return this.isBrowser() ? (localStorage.getItem('role') || undefined) : undefined;
  }

  set role(value: string | undefined) {
    this._role = value;
    if (this.isBrowser()) {
      localStorage.setItem('role', value || '');
    }
  }
  //Set user data after successful login
  setUserData(userData: { id: number, fullName: string, role?: string }) {
    this.userId = userData.id;
    this.userName = userData.fullName;
    this.role = userData.role;
  }

  // Clear user data on logout
  clearUserData() {
    this._userId = null;
    this._userName = null;
    this._role = undefined;
    if (this.isBrowser()) {
      localStorage.removeItem('userId');
      localStorage.removeItem('userName');
      localStorage.removeItem('role');
    }
  }
  // Check if the user is logged in
  isLoggedIn(): boolean {
    return this.userId !== null;
  }

  isAdmin(): boolean {
    return this.role === 'ADMIN';
  }
}
