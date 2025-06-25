import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { SigninComponent } from './signin/signin.component';
import { ErrorComponent } from './error/error.component';
import { AdminComponent } from './pages/admin/admin.component';
import { StudentComponent } from './pages/student/student.component';
import { FacultyComponent } from './pages/faculty/faculty.component';
import { CreateEnrollmentsComponent } from './pages/create-enrollments/create-enrollments.component';
import { CreateMarksComponent } from './pages/create-marks/create-marks.component';
import { CreateFeesComponent } from './pages/create-fees/create-fees.component';
import { CreateCoursesComponent } from './pages/create-courses/create-courses.component';
import { ListEntitiesComponent } from './shared/list-entities/list-entities.component';
import { authGuard } from '../guards/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full'},
  { path: 'admin', component: AdminComponent, canActivate: [authGuard] },
  { path: 'admin/listStudents', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'admin/listFaculty', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'admin/listCourses', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'admin/listEnrollments', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'faculty', component: FacultyComponent, canActivate: [authGuard] },
  { path: 'faculty/listFaculty', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'faculty/listStudents', component: ListEntitiesComponent , canActivate: [authGuard]},
  { path: 'faculty/listCourses', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'faculty/listEnrollments', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'student', component: StudentComponent , canActivate: [authGuard]},
  { path: 'student/listFaculty', component: ListEntitiesComponent , canActivate: [authGuard]},
  { path: 'student/listCourses', component: ListEntitiesComponent , canActivate: [authGuard]},
  { path: 'student/listEnrollments', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'student/listFees', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'student/listMarks', component: ListEntitiesComponent, canActivate: [authGuard] },
  { path: 'createEnrollments', component: CreateEnrollmentsComponent, canActivate: [authGuard] },
  { path: 'createMarks', component: CreateMarksComponent , canActivate: [authGuard]},
  { path: 'createFees', component: CreateFeesComponent , canActivate: [authGuard]},
  { path: 'createCourses', component: CreateCoursesComponent , canActivate: [authGuard]},
  { path: 'register', component: SignupComponent },
  { path: 'login', component: SigninComponent },
  { path: '**', component: ErrorComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
