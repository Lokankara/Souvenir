import { Injectable } from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToggleService {
  private toggleSubject = new Subject<boolean>();
  toggle$ = this.toggleSubject.asObservable();

  toggleVisibility(isVisible: boolean): void {
    this.toggleSubject.next(isVisible);
  }
}
