import {ModuleWithProviders, NgModule} from '@angular/core';
import {CoreRoutingModule} from '@app/core/core-routing-module';
import {FooterComponent} from '@app/core/components/footer/footer.component';
import {HeaderModule} from '@app/core/components/header/header.module';
import {SharedModule} from '@app/shared/shared.module';

@NgModule({
  declarations: [],
  exports: [FooterComponent, HeaderModule],
  imports: [
    HeaderModule,
    CoreRoutingModule,
    SharedModule,
    FooterComponent
  ]
})

export class CoreModule {
  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: []
    };
  }
}
