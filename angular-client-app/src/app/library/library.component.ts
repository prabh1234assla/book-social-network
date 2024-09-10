import { Component } from '@angular/core';
import { HttpProviderService } from '../service/http-provider.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
export class LibraryComponent {
  constructor(private httpProvider: HttpProviderService) { }

  booksJson: any[] = [];

  async jsonBooks() {
    this.httpProvider.getLibrary().subscribe({
      next: data => {
        console.log(data.body)
        this.booksJson = data.body
      },
      error: error => {
        alert('There was an error! ' + error);
      }
    });
  }

  ngOnInit(): void {
    this.jsonBooks();
  }

  hasBooksForGenre(genresLabel: string): boolean {
    return this.booksJson && this.booksJson.some(book => book.labels.includes(genresLabel) && !book.isBorrowed);
  }

  borrowBook(id: number): void {
    const data = [
      {
        "op": "replace",
        "path": "/borrowed",
        "value": true
      }
    ];
    
    this.httpProvider.borrowBook(id, data).subscribe({
      next: data => {
        console.log(data.body)
        // this.booksJson = data.body
      },
      error: error => {
        console.log(error);
        alert('There was an error! ' + error);
      }
    });
  }

  genresLabelsList = ['tale', 'poem', 'story', 'folklore',
    'novel', 'tragedy', 'gothic', 'fiction',
    'social', 'realism', 'political', 'satire',
    'modern', 'autobiography', 'saga', 'epic', 'essay',
    'post-apocalyptic', 'dystopia', 'Bildungsroman', 'Monogatari']
}
