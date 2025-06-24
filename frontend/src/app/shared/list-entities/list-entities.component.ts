import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-list-entities',
  templateUrl: './list-entities.component.html',
  styleUrl: './list-entities.component.sass'
})
export class ListEntitiesComponent {
  @Input() rowEntries: any[] = [];
  columnHeaders: string[] = Object.keys(this.rowEntries[0]);
}
