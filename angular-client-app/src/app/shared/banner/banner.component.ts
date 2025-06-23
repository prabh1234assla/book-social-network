import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrl: './banner.component.sass'
})
export class BannerComponent implements OnInit {
  @Input() imagePath: string = '';
  @Input() bannerText: string = '';
  @Input() routeLink: string = '';
  word1: string = '';
  word2: string = '';

  ngOnInit(): void {
    this.routeLink = '/' + this.routeLink
    this.assignWords();
  }
  
  generateWords(): Array<string> {
    return this.bannerText.split('_');
  }

  assignWords(): void {
    let words = this.generateWords();
    this.word1 = words[0][0].toUpperCase() + words[0].slice(1)
    this.word2 = words[1][0].toUpperCase() + words[1].slice(1)
  }

}
