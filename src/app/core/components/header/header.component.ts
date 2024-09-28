import {Component} from '@angular/core';
import {NgClass, NgForOf, NgStyle} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ToggleService} from '@app/shared/service/toggle.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    NgForOf,
    NgClass,
    NgStyle,
    ReactiveFormsModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  isSearchVisible = true;

  constructor(
    private toggleService: ToggleService) {
  }

  toggleModal(event: Event): void {
    event.preventDefault();
    this.isSearchVisible = !this.isSearchVisible;
    this.toggleService.toggleVisibility(this.isSearchVisible);
  }
}
