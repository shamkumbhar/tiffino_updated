import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap, catchError, throwError } from 'rxjs';
import { User } from '../models/User';
import { UserLogin } from '../interfaces/Userlogin';
import { HttpClient } from '@angular/common/http';
import { UserRegister } from '../interfaces/Userregister';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.prod';



// const apiUrl = environment.apiUrl; // ✅ Use environment variable
const userKey = 'user'; // Key for local storage

@Injectable({
  providedIn: 'root',
})


export class RegistrationService {
  private apiUrl = environment.apiUrl; // ✅ Use environment variable
  currentiser!: User;
  private userSubject = new BehaviorSubject<User>(this.getUserFromLocalStorage());
  public userObservable: Observable<User>;


  constructor(private http: HttpClient, private router: Router) {
    this.userObservable = this.userSubject.asObservable();
  }

  public get currentUser(): User {
    return this.userSubject.value;
  }

  // Register a new user
  register(formData: UserRegister): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/users/register`, formData).pipe(
      tap({
        next: (user) => {
          user.isAdmin = user.role === 'ADMIN';
          this.setUserToLocalStorage(user);
          this.userSubject.next(user);
          console.log(`User registered successfully: ${user.id}`);
        },
        error: (error) => {
          if (error.error instanceof ProgressEvent) {
            console.error('Registration failed: Backend unreachable or network error', error);
          } else {
            console.error('Registration failed:', error.error);
          }
        },
      })
    );
  }

  login(userLogin: UserLogin): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/users/login`, userLogin).pipe(
      tap({
        next: (user) => {
          localStorage.setItem('UserData', JSON.stringify(user));
          this.currentiser = user;
          this.setUserToLocalStorage(user);
          this.userSubject.next(user);
        },
        error: (errorResponse) => {
          console.log(errorResponse.error, 'Login Failed');
        },
      })
    );
  }

  checkEmailExists(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/users/email-exists?email=${email}`);
  }

  private getUserFromLocalStorage(): User {
    const userJson = localStorage.getItem(userKey);
    if (userJson) {
      return JSON.parse(userJson) as User;
    }
    return new User();
  }

  private setUserToLocalStorage(user: User): void {
    localStorage.setItem(userKey, JSON.stringify(user));
  }

  logout(): void {
    this.userSubject.next(new User());
    localStorage.removeItem(userKey);
    console.log('User has logged out successfully.');
  }

  changePassword(data: { currentPassword: string; newPassword: string }): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = { Authorization: `Bearer ${token}` };
    const baseurl = `${this.apiUrl}/users/{id}`;

    return this.http.post(`${baseurl}/change-password`, data, { headers });
  }

  get currentUserValue(): User {
    if (!this.currentiser) {
      const userData = localStorage.getItem('user');
      if (userData) this.currentiser = JSON.parse(userData);
    }
    return this.currentUser;
  }
}
