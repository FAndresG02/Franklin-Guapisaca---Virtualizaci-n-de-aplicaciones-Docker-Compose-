package ec.edu.ups.ppw64.demo64.business;

import java.util.List;

import ec.edu.ups.ppw64.demo64.dao.ProductoDAO;
import ec.edu.ups.ppw64.demo64.model.Producto;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionProductos {
	
private int ultimoCodigo = 0;
	
	@Inject
	private ProductoDAO daoProducto;

	public void guardarProductos(Producto producto) {
		if (producto.getCodigo() == 0) {
            // Asignar un nuevo código único al producto
            producto.setCodigo(++ultimoCodigo);
        }
		
		Producto cli = daoProducto.read(producto.getCodigo());
		if (cli != null){
			daoProducto.update(producto);
		}else {
			daoProducto.insert(producto);
		}
	}
	
	public void actualizarProducto(Producto producto) throws Exception {
		Producto cli = daoProducto.read(producto.getCodigo());
		if (cli != null){
			daoProducto.update(producto);
		}else {
			throw new Exception("Producto no existe");
		}
	}
	
	public Producto getProductoPorNombre(String nombre) throws Exception {
	    if(nombre.isEmpty()) {
	        throw new Exception("El nombre del producto no puede estar vacío");
	    }
	    
	    return daoProducto.getProductoPorNombre(nombre);
	}
	
	public void borrarProducto(int codigo){
		
		daoProducto.remove(codigo);
	}
	
	public List<Producto> getProductos(){
		return daoProducto.getAll();
	}
}
