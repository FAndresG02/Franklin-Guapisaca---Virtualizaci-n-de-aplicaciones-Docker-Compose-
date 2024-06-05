import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductosComponent } from './productos/productos.component';

const routes: Routes = [
  { path: '', component: ProductosComponent  },
  { path: 'productos', component: ProductosComponent },
  { path: '**', redirectTo: '/productos' }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
