import { Component, OnInit} from '@angular/core';
import { Producto, ServicesService } from '../services.service';


@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})

export class ProductosComponent implements OnInit{
  productos: Producto[] = [];
  nuevoProducto: Producto = {};
  editMode: boolean = false;

  constructor(private servicesService: ServicesService) { }

  ngOnInit(): void {
    this.obtenerProductos();
  }

  obtenerProductos(): void {
    this.servicesService.getProductos().subscribe(
      data => this.productos = data,
      error => console.error('Error al obtener los productos', error)
    );
  }

  crearOActualizarProducto(): void {
    if (this.editMode) {
      this.servicesService.actualizarProducto(this.nuevoProducto).subscribe(
        response => {
          console.log('Producto actualizado', response);
          this.resetForm();
          this.obtenerProductos();
        },
        error => console.error('Error al actualizar el producto', error)
      );
    } else {
      this.servicesService.crearProducto(this.nuevoProducto).subscribe(
        response => {
          console.log('Producto creado', response);
          this.resetForm();
          this.obtenerProductos();
        },
        error => console.error('Error al crear el producto', error)
      );
    }
  }

  iniciarActualizacion(producto: Producto): void {
    this.nuevoProducto = { ...producto };
    this.editMode = true;
  }

  borrarProducto(codigo: number | undefined): void {
    if (codigo !== undefined) {
      this.servicesService.borrarProducto(codigo).subscribe(
        response => {
          console.log('Producto eliminado', response);
          this.obtenerProductos();
        },
        error => console.error('Error al eliminar el producto', error)
      );
    } else {
      console.error('Código de producto no puede ser undefined');
    }
  }

  resetForm(): void {
    this.nuevoProducto = {};
    this.editMode = false;
  }
  
}
