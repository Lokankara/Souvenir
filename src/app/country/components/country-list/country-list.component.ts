import {Component, HostListener, OnInit} from '@angular/core';
import {CountryService} from '@app/country/services/country.service';
import {Country} from '@app/country/models/country.model';
import {CountryCardComponent} from '@app/country/components/country-card/country-card.component';
import {AsyncPipe, NgForOf, NgIf} from '@angular/common';
import {Router} from '@angular/router';
import {ToggleService} from '@app/shared/service/toggle.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-country-list',
  standalone: true,
  imports: [
    CountryCardComponent,
    NgForOf,
    AsyncPipe,
    FormsModule,
    NgIf
  ],
  templateUrl: './country-list.component.html',
  styleUrl: './country-list.component.css'
})
export class CountryListComponent implements OnInit {
  searchTerm = '';
  isVisible = true;
  countries: Country[] = [];
  filteredCountries: Country[] = [];

  constructor(
    private router: Router,
    private toggleService: ToggleService,
    private countryService: CountryService) {
  }

  @HostListener('window:keydown.escape')
  onEscapeKey(): void {
    this.toggleService.toggleVisibility(false);
  }

  ngOnInit(): void {

    this.toggleService.toggle$.subscribe(isVisible => {
      this.isVisible = isVisible;
    });

    this.countryService.getCountries().subscribe(countries => {
      this.filteredCountries = this.countries = countries;
    });
  }

  search(): void {
    this.countries = this.filteredCountries.filter(country =>
      country.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  navigateToCountry(countryCode: string): void {
    localStorage.setItem('selectedCountryCode', countryCode);
    this.router.navigate(['/country', countryCode]);
  }
}
