import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {baseUrl} from '@app/config';
import {Observable} from 'rxjs';
import {Country} from '@app/country/models/country.model';
import {CountryInfo} from '@app/country/models/country-info.model';
import {PublicHoliday} from '@app/country/models/public-holiday.model';
import {LongWeekend} from '@app/country/models/long-weekend.model';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  constructor(private http: HttpClient) {
  }

  getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(`${baseUrl}/AvailableCountries`);
  }

  getCountryInfo(countryCode: string): Observable<CountryInfo> {
    return this.http.get<CountryInfo>(`${baseUrl}/CountryInfo/${countryCode}`);
  }

  getPublicHolidays(countryCode: string, year: number): Observable<PublicHoliday[]> {
    return this.http.get<PublicHoliday[]>(`${baseUrl}/PublicHolidays/${year}/${countryCode}`);
  }

  getLongWeekends(countryCode: string, year: number): Observable<LongWeekend[]> {
    return this.http.get<LongWeekend[]>(`${baseUrl}/LongWeekend/${year}/${countryCode}`);
  }
}
