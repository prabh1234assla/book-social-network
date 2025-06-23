import { Component, Input } from '@angular/core';

export class Banner{
  imagePath: string
  bannerText: string

  constructor(bannerText: string, viewFlag: boolean = true){
    let suffix = "create_";
    if (viewFlag)
        suffix = "list_";
      this.imagePath = `assets/portals/${suffix+bannerText}.jpg`;
      this.bannerText = suffix+bannerText;
  }
}

@Component({
  selector: 'app-portals',
  templateUrl: './portals.component.html',
  styleUrl: './portals.component.sass'
})
export class PortalsComponent {
  @Input() title: string = '';
  @Input() subtitle2: string = '';
  @Input() banners1: Array<Banner> = [];
  @Input() subtitle1: string = '';
  @Input() banners2: Array<Banner> = [];
}
