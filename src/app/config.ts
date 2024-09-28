import {environment} from '../environments/environment';
import {InjectionToken} from '@angular/core';

export const baseUrl: string = environment.baseUrl;
export const itemSize: number = environment.itemSize;
export const ITEM_SIZE_TOKEN: InjectionToken<string> = new InjectionToken<string>('ITEM_SIZE_TOKEN');
export const BASE_URL_TOKEN: InjectionToken<string> = new InjectionToken<string>('BASE_URL_TOKEN');
