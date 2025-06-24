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
import { StudentComponent as listAdminStudentComponent } from './pages/listadminEntities/student/student.component';

const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full'},
  { path: 'admin', component: AdminComponent },
  { path: 'admin/listStudents', component: listAdminStudentComponent },
  { path: 'student', component: StudentComponent },
  { path: 'faculty', component: FacultyComponent },
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
