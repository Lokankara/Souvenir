import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {NotFoundComponent} from '@app/core/pages/not-found/not-found.component';
import {HomeComponent} from '@app/country/pages/home/home.component';
import {PreloadService} from '@app/shared/service/preload.service';
import {CountryCardComponent} from '@app/country/components/country-card/country-card.component';

export const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'not-found', component: NotFoundComponent},
  {path: 'home', component: HomeComponent},
  {path: 'country', component: CountryCardComponent},
  {path: 'country/:countryCode', loadChildren: () => import('./country/country.module').then(m => m.CountryModule)},
  {path: '**', redirectTo: 'not-found'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadService})],
  exports: [RouterModule],
  providers: []
})

export class AppRoutingModule {
}
