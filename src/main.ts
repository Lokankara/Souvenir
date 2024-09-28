import {bootstrapApplication} from '@angular/platform-browser';
import {AppComponent} from '@app/app.component';
import {environment} from './environments/environment';
import {appConfig} from '@app/app.config';

bootstrapApplication(AppComponent, appConfig).then(() => {
  if ('serviceWorker' in navigator && environment.production) {
    navigator.serviceWorker.register('/service-worker.js')
      .then((registration) => {
        console.log('Service Worker registered with scope: ', registration.scope);
      })
      .catch((error) => {
        console.error('Service Worker registration failed: ', error);
      });
  }
}).catch((err) => console.error('Application bootstrap failed:', err));
