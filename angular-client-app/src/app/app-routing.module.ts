import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LibraryComponent } from './library/library.component';
import { ReadersListComponent } from './readers-list/readers-list.component';
import { SignupComponent } from './signup/signup.component';
import { SigninComponent } from './signin/signin.component';
import { ErrorComponent } from './error/error.component';

const routes: Routes = [
  { path: '', redirectTo: 'readersList', pathMatch: 'full'},
  { path: 'readersList', component: ReadersListComponent },
  { path: 'library', component: LibraryComponent },
  { path: 'register', component: SignupComponent },
  { path: 'login', component: SigninComponent },
  { path: '**', component: ErrorComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
