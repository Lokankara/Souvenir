import { HttpInterceptorFn } from '@angular/common/http';
import {catchError, throwError} from 'rxjs';

export const httpInterceptorService: HttpInterceptorFn = (req, next) => {
  const modifiedReq = req.clone({
    setHeaders: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
  });

  return next(modifiedReq).pipe(
    catchError((error) => {
      console.error('HTTP error occurred:', error);
      return throwError(() => error);
    })
  );
};
