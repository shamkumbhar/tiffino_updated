import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.prod';


@Injectable({ providedIn: 'root' })
export class VoucherService {
   private apiUrl = environment.apiUrl; // ✅ Use environment variable
 

  constructor(private http: HttpClient) {}

  getVoucherByCodes(code: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/vouchers/byCode/${code}`);
  }

  getallvouchers(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/vouchers/getall`);
  }
}
