import { Component } from '@angular/core';
import { HttpProviderService } from '../../service/http-provider.service';

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.sass'
})
export class ProfileMenuComponent {
  isVisible: boolean = false;
  data: Record<any, any> = {};
  username: string = '';
  email: string = '';
  currentSemester: number | null = null;
  studentEnrollmentStatus: boolean = false;
  role: string = '';
  homePage: string = '';

  showCard() {
    this.isVisible = true;
  }

  hideCard() {
    this.isVisible = false;
  }

  constructor(private httpProvider: HttpProviderService) { }

  async getDataList() {
    //@ts-ignore
    this.httpProvider.getMyself().subscribe({
      //@ts-ignore
      next: data => {
        this.data = data.body;

        this.username = this.data['username'];
        this.email = this.data['email'];
        this.role = this.data["role"];

        if (this.role === 'STUDENT')
          this.homePage = '/student'
        else if (this.role === 'FACULTY')
          this.homePage = '/faculty'
        else
          this.homePage = '/student'

        if(this.role == 'STUDENT'){
          this.currentSemester = this.data['semester'];
          this.studentEnrollmentStatus = this.data['semesterRegistrationDone']
        }
      },
      //@ts-ignore
      error: error => {
        alert('There was an error! ' + error);
      }
    })

  }

  ngOnInit(): void {
    this.getDataList();
  }

}
