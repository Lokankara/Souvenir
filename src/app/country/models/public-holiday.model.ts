export interface PublicHoliday {
  date: string;
  localName: string;
  name: string;
  countryCode: string;
  global: boolean;
  counties: string[];
  launchYear: number;
  types: string[];
}