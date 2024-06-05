package ec.edu.ups.ppw64.demo64.business;

import java.util.Date;
import java.util.List;

import ec.edu.ups.ppw64.demo64.dao.ProductoDAO;
import ec.edu.ups.ppw64.demo64.model.Producto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class GestionDatos {

	@Inject
	private ProductoDAO daoProducto;
	
	@PostConstruct
	public void init() {
		System.out.println("iniciando");
		
		Producto producto = new Producto();
		producto.setCodigo(1);
		producto.setPrecio("23");
		producto.setDescripcion("Ropa de hombre");
		producto.setNombre("Camisa");
		
		daoProducto.insert(producto);
		
		producto = new Producto();
		producto.setCodigo(2);
		producto.setPrecio("56");
		producto.setDescripcion("Ropa de mujer");
		producto.setNombre("Blusa");
		
		daoProducto.insert(producto);
		
	}
}
