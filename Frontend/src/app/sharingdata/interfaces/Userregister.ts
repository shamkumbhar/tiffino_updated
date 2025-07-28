export interface Address {
  addressLine1: string;
  addressLine2?: string;
  city: string;
  state: string;
  pinCode: string;
  latitude: number;
  longitude: number;
  isDefault: boolean;
  addressType: string; // e.g., 'Home', 'Work'
}

export interface UserRegister {
  firstName: string;
  lastName: string;
  fullName: string;
  gender: string;
  email: string;
  phoneNumber: string;
  password: string;
  role: string[]; // form has roles as an array
  isActive: boolean;
  dietaryPreferences: string[]; // dropdown is multi-select
  allergenPreferences: string[]; // dropdown is multi-select
  profileImageUrl: string; // filename or URL string
  /*dateJoined: string; // ISO date string (e.g. '2024-07-07')
  lastLogin: string; */ // ISO date string
  addresses: Address[];
}