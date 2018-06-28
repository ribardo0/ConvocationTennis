import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ChampionnatComponent }   from '../championnat/championnat.component';

const routes: Routes = [
  { path: '', component: ChampionnatComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
