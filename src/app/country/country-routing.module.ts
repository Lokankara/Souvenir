import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {CountryCardComponent} from '@app/country/components/country-card/country-card.component';

const routes: Routes = [
  {
    path: '',
    component: CountryCardComponent,
    children: [
      { path: 'country/:countryCode', loadChildren: () => import('./country.module').then(m => m.CountryModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CountryRoutingModule {
}
