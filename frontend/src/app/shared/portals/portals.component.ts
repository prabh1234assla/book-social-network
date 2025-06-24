import { Component, Input } from '@angular/core';

export class Banner{
  imagePath: string
  bannerText: string
  routeLink: string

  constructor(bannerText: string, viewFlag: boolean = true){
    let suffix = "create";
    if (viewFlag)
        suffix = "list";
      this.imagePath = `assets/portals/${suffix+'_'+bannerText}.jpg`;
      this.bannerText = suffix+'_'+bannerText;
      this.routeLink = suffix + (bannerText[0].toUpperCase() + bannerText.slice(1).toLowerCase())
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
