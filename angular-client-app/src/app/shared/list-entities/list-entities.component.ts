import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-list-entities',
  templateUrl: './list-entities.component.html',
  styleUrl: './list-entities.component.sass'
})
export class ListEntitiesComponent {
  @Input() columnHeaders: string[] = [];
  @Input() rowEntries: any[] = [];
}
