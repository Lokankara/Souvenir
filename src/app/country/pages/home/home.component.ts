import {Component} from '@angular/core';
import {CountryListComponent} from '@app/country/components/country-list/country-list.component';
import {RandomComponent} from '@app/country/components/random/random.component';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    CountryListComponent,
    RandomComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
