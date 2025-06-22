import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { SigninComponent } from './signin/signin.component';
import { ErrorComponent } from './error/error.component';

const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full'},
  { path: 'register', component: SignupComponent },
  { path: 'login', component: SigninComponent },
  { path: '**', component: ErrorComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
