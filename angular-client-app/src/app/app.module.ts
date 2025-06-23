import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { SigninComponent } from './signin/signin.component';
import { ErrorComponent } from './error/error.component';
import { provideHttpClient } from '@angular/common/http';
import { BannerComponent } from './shared/banner/banner.component';
import { StudentComponent } from './pages/student/student.component';
import { FacultyComponent } from './pages/faculty/faculty.component';
import { AdminComponent } from './pages/admin/admin.component';
import { PortalsComponent } from './shared/portals/portals.component';
import { InputBoxComponent } from './shared/input-box/input-box.component';
import { CreateTicketComponent } from './shared/create-ticket/create-ticket.component';
import { ViewEntitiesComponent } from './shared/view-entities/view-entities.component';
import { CreateEnrollmentsComponent } from './pages/create-enrollments/create-enrollments.component';
import { CreateFeesComponent } from './pages/create-fees/create-fees.component';
import { CreateMarksComponent } from './pages/create-marks/create-marks.component';
import { CreateCoursesComponent } from './pages/create-courses/create-courses.component';
import { ListEntitiesComponent } from './shared/list-entities/list-entities.component';
import { CoursesComponent } from './pages/listadminEntities/courses/courses.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    SigninComponent,
    ErrorComponent,
    BannerComponent,
    StudentComponent,
    FacultyComponent,
    AdminComponent,
    PortalsComponent,
    InputBoxComponent,
    CreateTicketComponent,
    ViewEntitiesComponent,
    CreateEnrollmentsComponent,
    CreateFeesComponent,
    CreateMarksComponent,
    CreateCoursesComponent,
    ListEntitiesComponent,
    CoursesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
