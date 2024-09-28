import {Component, Input, OnInit} from '@angular/core';
import {Country} from '@app/country/models/country.model';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {forkJoin, of, switchMap} from 'rxjs';
import {CountryService} from '@app/country/services/country.service';
import {PublicHoliday} from '@app/country/models/public-holiday.model';
import {CountryInfo} from '@app/country/models/country-info.model';
import {HttpErrorResponse} from '@angular/common/http';
import {NgForOf, NgIf} from '@angular/common';

const START_YEAR = 2020;

@Component({
  selector: 'app-country-card',
  standalone: true,
  imports: [
    RouterLink,
    NgForOf,
    NgIf
  ],
  templateUrl: './country-card.component.html',
  styleUrl: './country-card.component.css'
})
export class CountryCardComponent implements OnInit {
  countryCode = '';
  countryInfo: CountryInfo | null = null;
  holidays: PublicHoliday[] = [];
  years: number[] = [];
  selectedYear: number = new Date().getFullYear();
  @Input() country!: Country;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private countryService: CountryService) {
  }

  ngOnInit(): void {
    this.years = Array.from({length: 11}, (_, i) => START_YEAR + i);
    this.route.paramMap.pipe(
      switchMap(params => {
        this.countryCode = params.get('countryCode')
          || localStorage.getItem('selectedCountryCode')
          || navigator.language.split('-')[0];
        if (this.countryCode) {
          return forkJoin([
            this.countryService.getCountryInfo(this.countryCode),
            this.countryService.getPublicHolidays(this.countryCode, this.selectedYear)
          ]);
        }
        return of([null, []] as [CountryInfo | null, PublicHoliday[]]);
      })
    ).subscribe({
      next: ([countryInfo, holidays]) => {
        this.countryInfo = countryInfo;
        this.holidays = holidays;
      },
      error: this.handleError.bind(this)
    });
  }

  setYear(year: number): void {
    this.selectedYear = year;
    if (this.countryCode) {
      this.countryService.getPublicHolidays(this.countryCode, year)
        .subscribe({
          next: this.handleHolidays.bind(this),
          error: this.handleError.bind(this)
        });
    }
  }

  handleHolidays(holidays: PublicHoliday[]): void {
    this.holidays = holidays;
  }

  handleError(err: HttpErrorResponse): void {
    console.error('Error fetching data:', err.message);
  }

  formatDateWeek(date: string) {
    const options: Intl.DateTimeFormatOptions = {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    };
    return new Date(date).toLocaleDateString('en-US', options);
  }
}
