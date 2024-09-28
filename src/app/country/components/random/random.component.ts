import {Component, OnInit} from '@angular/core';
import {AsyncPipe, NgClass, NgForOf, NgIf} from '@angular/common';
import {RandomCountry} from '@app/country/models/random-country.model';
import {Router} from '@angular/router';
import {CountryService} from '@app/country/services/country.service';
import {PublicHoliday} from '@app/country/models/public-holiday.model';
import {Country} from '@app/country/models/country.model';
import {CountryCardComponent} from '@app/country/components/country-card/country-card.component';
import {FormsModule} from '@angular/forms';

const MAX_LENGTH = 3;
const AVERAGE = 0.5;

@Component({
  selector: 'app-random',
  standalone: true,
  imports: [
    AsyncPipe,
    NgForOf,
    NgIf,
    CountryCardComponent,
    FormsModule,
    NgClass
  ],
  templateUrl: './random.component.html',
  styleUrl: './random.component.css'
})
export class RandomComponent implements OnInit {
  now: Date = new Date();
  randomCountries: RandomCountry[] = [];
  currentYear: number = this.now.getFullYear();

  constructor(
    private router: Router,
    private countryService: CountryService) {
  }

  ngOnInit(): void {
    this.fetchRandomCountries();
  }

  navigateToCountry(countryCode: string): void {
    localStorage.setItem('selectedCountryCode', countryCode);
    this.router.navigate(['/country', countryCode]);
  }

  fetchRandomCountries(): void {
    this.countryService.getCountries().subscribe(countries => {
      this.getRandomElements(countries, MAX_LENGTH).forEach(country => {
        if (country.name) {
          this.countryService.getPublicHolidays(country.countryCode, this.currentYear)
            .subscribe((holidays: PublicHoliday[]) => {
              const upcomingHolidays = holidays.filter(holiday => new Date(holiday.date) >= this.now);
              if (upcomingHolidays.length !== 0) {
                this.setRandomCountries(upcomingHolidays[0], country.name);
              } else {
                this.getNextYearHoliday(country);
              }
            });
        } else {
          this.randomCountries.push({
            name: 'Not Found',
            countryCode: '',
            nextHoliday: '',
            holidayDate: ''
          });
        }
      });
    });
  }

  private getNextYearHoliday(country: Country) {
    this.countryService.getPublicHolidays(country.countryCode, this.currentYear + 1)
      .subscribe((nextYearHolidays: PublicHoliday[]) =>
        this.setRandomCountries(nextYearHolidays[0], country.name));
  }

  private getRandomElements<T>(array: T[], count: number): T[] {
    return array.sort(() => AVERAGE - Math.random()).slice(0, count);
  }

  private setRandomCountries(holiday: PublicHoliday, countryName: string): void {
    this.randomCountries.push({
      name: countryName,
      countryCode: holiday.countryCode,
      nextHoliday: holiday.name,
      holidayDate: holiday.date
    });
  }
}
