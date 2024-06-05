import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})

export class ServicesService {
  private apiUrl = `${environment.WA_PATH}/productos`;

  constructor(private http: HttpClient) { }

  crearProducto(producto: Producto): Observable<any> {
    return this.http.post<any>(this.apiUrl, producto, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  actualizarProducto(producto: Producto): Observable<any> {
    return this.http.put<any>(this.apiUrl, producto, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  borrarProducto(codigo: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}?id=${codigo}`);
  }

  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/list`);
  }

  getProductoPorNombre(nombre: string, precio: string): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${nombre}/${precio}`);
  }
  
}

export class Producto {
  codigo?: number;
  nombre?: string;
  descripcion?: string;
  precio?: string;
}

