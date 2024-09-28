import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { CountryModule } from './country/country.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule.forRoot(),
    SharedModule.forRoot(),
    CountryModule.forRoot(),
    BrowserAnimationsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !environment.production,
      registrationStrategy: 'registerWhenStable:30000'
    }),
    StoreDevtoolsModule.instrument({
      maxAge: 25, logOnly: !environment.production,
      features: {
        pause: false,
        lock: true,
        export: true
      }
    })
  ],
  providers: []
})
export class AppModule { }
