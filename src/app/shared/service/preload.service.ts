import {Injectable} from '@angular/core';
import {PreloadAllModules, Route} from '@angular/router';
import {delay, mergeMap, Observable, of} from 'rxjs';

const PRELOAD_DELAY_MS = 5000;

@Injectable()
export class PreloadService implements PreloadAllModules {
  public preload(route: Route, fn: () => Observable<unknown>): Observable<unknown> {
    return of(route).pipe(
      delay(PRELOAD_DELAY_MS),
      mergeMap(() => fn())
    );
  }
}
