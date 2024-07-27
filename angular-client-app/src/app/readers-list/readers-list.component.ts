import { Component } from '@angular/core';
import booksJson from "../../assets/json/booksData.json";

@Component({
  selector: 'app-readers-list',
  templateUrl: './readers-list.component.html',
  styleUrl: './readers-list.component.sass'
})
export class ReadersListComponent {
  booksJson = booksJson

  genresLabelsList = ['tale', 'poem', 'story', 'folklore',
    'novel', 'tragedy', 'gothic', 'fiction',
    'social', 'realism', 'political', 'satire',
    'modern', 'autobiography', 'saga', 'epic', 'essay',
    'post-apocalyptic', 'dystopia', 'Bildungsroman', 'Monogatari']
  
}
