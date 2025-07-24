import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminDashboardComponent } from './dashboard/admin-dashboard.component';
import { AuthGuard } from './services/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'admin-dashboard', component: AdminDashboardComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '/login' }  // Redirect unknown routes
];
