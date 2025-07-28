import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SubscriptionPayload } from '../models/subscription';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
@Injectable({ providedIn: 'root' })
export class SubscriptionService {
  private baseUrl = 'http://localhost:8960';

  constructor(private http: HttpClient) {}

getActivePlans(): Observable<any[]> {
    return this.http.get<any[]>('/subscription-plans/getall');
  }
  searchPlans(): Observable<any[]> {
  return this.http.get<any[]>('/subscription-plans/search-plan');
}
getusersplan(userId: number): Observable<any[]> {
  return this.http.get<any[]>(`/subscriptions/user/${userId}`);
}

 pause(subscriptionId: number, token: string): Observable<any> {
  const headers = new HttpHeaders({
    Authorization: `Bearer ${token}`
  });

  return this.http.put(`${this.baseUrl}/subscriptions/${subscriptionId}/pause`, {}, { headers });
}

 resume(subscriptionId: number): Observable<any> {
  

  return this.http.put(`/subscriptions/${subscriptionId}/resume`, {});
}
  cancel(subscriptionId: number,token: string) {
    const headers = new HttpHeaders({
    Authorization: `Bearer ${token}`
  });
    return this.http.put(`${this.baseUrl}/subscriptions/${subscriptionId}/cancel`, {},{ headers });
  }

  getSubscriptionsByUser(userId: number,token: string) {
    return this.http.get(`${this.baseUrl}/subscriptions/user/${userId}`,{});
  }


  createsubscriptions(payload:any){
    return this.http.post('/subscription-plans/create',payload);
  }
 deactivatesubscription(planId:any){
    return this.http.put(`/subscription-plans/deactivate/${planId}`,{}).subscribe({
      next:(data)=>{alert('subscription deactivated')},
      error:(err)=>{alert('subscription deactivation failed')}
    });
  }
  deleteplan(planId:any){
     return this.http.delete(`/subscription-plans/delete/${planId}`,{})
  }
}