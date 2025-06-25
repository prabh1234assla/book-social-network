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

const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full'},
  { path: 'admin', component: AdminComponent },
  { path: 'admin/listStudents', component: ListEntitiesComponent },
  { path: 'admin/listFaculty', component: ListEntitiesComponent },
  { path: 'admin/listCourses', component: ListEntitiesComponent },
  { path: 'admin/listEnrollments', component: ListEntitiesComponent },
  { path: 'faculty', component: FacultyComponent },
  { path: 'faculty/listFaculty', component: ListEntitiesComponent },
  { path: 'faculty/listStudents', component: ListEntitiesComponent },
  { path: 'faculty/listCourses', component: ListEntitiesComponent },
  { path: 'faculty/listEnrollments', component: ListEntitiesComponent },
  { path: 'student', component: StudentComponent },
  { path: 'student/listFaculty', component: ListEntitiesComponent },
  { path: 'student/listCourses', component: ListEntitiesComponent },
  { path: 'student/listEnrollments', component: ListEntitiesComponent },
  { path: 'student/listFees', component: ListEntitiesComponent },
  { path: 'student/listMarks', component: ListEntitiesComponent },
  { path: 'createEnrollments', component: CreateEnrollmentsComponent },
  { path: 'createMarks', component: CreateMarksComponent },
  { path: 'createFees', component: CreateFeesComponent },
  { path: 'createCourses', component: CreateCoursesComponent },
  { path: 'register', component: SignupComponent },
  { path: 'login', component: SigninComponent },
  { path: '**', component: ErrorComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
