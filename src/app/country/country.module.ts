import {ModuleWithProviders, NgModule} from '@angular/core';
import {CountryCardComponent} from '@app/country/components/country-card/country-card.component';
import {CountryRoutingModule} from '@app/country/country-routing.module';
import {CommonModule} from '@angular/common';

@NgModule({
  declarations: [],
  exports: [],
  imports: [
    CommonModule,
    CountryRoutingModule,
    CountryCardComponent
  ]
})

export class CountryModule {
  static forRoot(): ModuleWithProviders<CountryModule> {
    return {
      ngModule: CountryModule,
      providers: []
    };
  }
}
