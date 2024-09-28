import {NgModule} from '@angular/core';
import {HeaderComponent} from '@app/core/components/header/header.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';

@NgModule({
  declarations: [],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    HeaderComponent
  ], exports: [
    HeaderComponent
  ]
})
export class HeaderModule {
}
