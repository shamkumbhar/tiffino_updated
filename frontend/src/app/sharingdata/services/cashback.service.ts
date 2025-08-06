import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.prod';


@Injectable({ providedIn: 'root' })
export class CashbackService {
  private apiUrl = environment.apiUrl; // ✅ Use environment variable
  private baseUrl = `${this.apiUrl}/reward`;

  constructor(private http: HttpClient) {}

  getRewardsByUserId(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/user/${userId}`);
  }
}

























// import { HttpClient } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';

// @Injectable({ providedIn: 'root' })
// export class CashbackService {
//   private baseUrl = '/reward';

//   constructor(private http: HttpClient) {}

//   getRewardsByUserId(userId: number): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/user/${userId}`);
//   }
// }