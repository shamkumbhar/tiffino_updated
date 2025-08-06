import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../sharingdata/services/login.service';  // Regular user service
import { AdminAuthService } from '../sharingdata/services/admin.service'; // Admin service (if needed)
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
@Component({
  selector: 'app-profile',
  standalone:true,
  imports:[CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
   userDetails: any = {};  // Holds the current user data
  token: string = '';      // To store the token
  userId: number | null = null; // To store user ID
  isLoading = false;
  errorMessage = '';
  showpopup = false;
 
  constructor(private registrationService: RegistrationService, private router: Router) {}
 
  ngOnInit(): void {
    this.isLoading = true;
 
    // Retrieve user and token data from localStorage
    const raw = localStorage.getItem('user');
   
    if (raw) {
      const parsed = JSON.parse(raw);
     
      // Get the user details and token
      this.userId = parsed.user?.id;  // Access the user's ID
      this.token = parsed.token;      // Access the stored token
      this.userDetails = parsed.user; // Assign user data to userDetails
 
      this.isLoading = false;
    } else {
      this.errorMessage = 'No user data found in localStorage.';
      this.isLoading = false;
    }
  }
 
  // Format the date to a readable string (e.g., "08-07-2025")
  formatDate(date: string): string {
    const d = new Date(date);
    return `${d.getDate()}-${d.getMonth() + 1}-${d.getFullYear()}`;
  }
 
  // Logout logic
  logout(): void {
    this.registrationService.logout(); // Call the logout method from the service
    localStorage.removeItem('user')
    this.router.navigate(['/login']); // Navigate to login page after logout

  }
}