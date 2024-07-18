import { Component } from '@angular/core';
import booksJson from "../../assets/json/booksData.json";

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
export class LibraryComponent {
  booksJson = booksJson

  genresLabelsList = ['tale', 'poem', 'story', 'folklore',
    'novel', 'tragedy', 'gothic', 'fiction',
    'social', 'realism', 'political', 'satire',
    'modern', 'autobiography', 'saga', 'epic', 'essay',
    'post-apocalyptic', 'dystopia', 'Bildungsroman', 'Monogatari']
}
