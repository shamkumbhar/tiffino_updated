import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { SubscriptionService } from '../sharingdata/services/subscription.service';
 
@Component({
  selector: 'app-subscription-form',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule],
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionComponent implements OnInit {
  form: FormGroup;
  plans: any[] = [];
 
  selectedPlanId: number | null = null;
  userId: number = 0;
  token: string = '';
   isLoading = false;
   showpopup = false;
  showErrorPopup = false;
   userData: any = '';
  errorMessage = '';
  subscriptionid:any;
  subscriptions:any[]=[];
  showActiveCard: boolean = false;
  activeSubscription:any=[];
  fb = inject(FormBuilder);
  http = inject(HttpClient);
  router = inject(Router);
  subs = inject(SubscriptionService);
 
  constructor() {
    this.form = this.fb.group({
      MealType:[''],
      BillingCycle:[''],
      SpiceLevel: [''],
      SubscriptionStatus:[''],
      /*portionSize: [''],*/
      autoRenew: [false],
      dietaryRestrictions: [''],
      /*additionalRequests: [''],
      totalInstallments: [null]*/
    });
  }
 
 
 
 
 
  ngOnInit(): void {
   
  this.getAllplans();
 
    const raw = localStorage.getItem('user');
    if (raw) {
      const parsed = JSON.parse(raw);
      this.userId = parsed.user?.id;
      this.token = parsed.token;
    }
     this.subs.getusersplan(this.userId).subscribe({
      next :(data:any[])=>{
        console.log(data);
       this.subscriptions = data;
      this.activeSubscription = data[0]; // Keep for other UI if needed
      this.subscriptionid = data[0].id;
      },
      error:(err)=>{
        console.log(err);
      }
     })
  }
 
 
  getAllplans(){
this.subs.getActivePlans().subscribe({
      next: (plans: any[]) => {
        this.plans = plans;
        console.log(plans);
      },
      error: (err) => console.error('Error fetching plans:', err)
    });
  }
 
 
 
 
  onSubmit(): void {
    this.errorMessage = '';
    this.showErrorPopup = false;
    if (this.form.invalid || !this.selectedPlanId) {
      alert('Please fill all fields and select a plan');
      this.form.markAllAsTouched();
      return;
    }
 
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`
    });
 
    const url = `/subscriptions/${this.userId}/subscribe/${this.selectedPlanId}`;
    this.http.post(url, this.form.value, { headers }).subscribe({
      next: (res) => {
        console.log(res);
       // this.router.navigate(['/payment'])
        this.userData=res;
     
        this.isLoading = false;
        this.showpopup = true;
        alert('Subscription created successfully!');
       
        setTimeout(() => {
          this.showpopup = false;
          this.form.reset();
          this.router.navigate(['/login']);
        }, 3000);
        this.selectedPlanId = null;
      },
      error: (err) => {
        console.error('Subscription failed:', err);
        this.isLoading = false;
        this.errorMessage = err?.error?.message || 'subscription Failed ';
        this.showErrorPopup = true;
        alert('Failed to create subscription.');
      }
    });
  }
 
pausesubscription(sub: any) {
  this.subs.pause(sub.id, this.token).subscribe({
    next: () => alert('Subscription paused successfully'),
    error: () => alert('Pause failed')
  });
}
 resumesubscription(sub: any) {
  this.subs.resume(sub.id).subscribe({
    next: () => alert('Subscription resumed successfully'),
    error: () => alert('Resume failed')
  });
}
cancelsubscription(sub: any) {
  this.subs.cancel(sub.id, this.token).subscribe({
    next: () => alert('Subscription cancelled successfully'),
    error: () => alert('Cancel failed')
  });
}
}