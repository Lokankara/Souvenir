import {ModuleWithProviders, NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {BASE_URL_TOKEN, baseUrl, itemSize, ITEM_SIZE_TOKEN} from '@app/config';
import {httpInterceptorService} from '@app/shared/service/http.interceptor.service';

@NgModule({
  declarations: [],
  imports: [],
  providers: [
    { provide: HTTP_INTERCEPTORS, useFactory: () => httpInterceptorService, multi: true }
  ]
})
export class SharedModule {
  public static forRoot(): ModuleWithProviders<SharedModule> {
    return {
      ngModule: SharedModule,
      providers: [
        {
          provide: BASE_URL_TOKEN,
          useValue: baseUrl,
          multi: true
        },
        {
          provide: ITEM_SIZE_TOKEN,
          useValue: itemSize,
          multi: true
        }
      ]
    };
  }

  public static forChild(): ModuleWithProviders<SharedModule> {
    return {
      ngModule:
      SharedModule
    };
  }
}
