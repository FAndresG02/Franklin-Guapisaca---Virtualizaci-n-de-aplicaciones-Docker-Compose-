package ec.edu.ups.ppw64.demo64.services;

import java.util.List;

import config.ConfigJaeger;
import ec.edu.ups.ppw64.demo64.business.GestionProductos;
import ec.edu.ups.ppw64.demo64.model.Producto;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("productos")
public class ProductoServices {

	private final Tracer tracer = GlobalTracer.get();
	
	@Inject
	private GestionProductos gProductos;
	
	@Inject
	private ConfigJaeger configjaeger;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear (Producto producto) {
		Span span = tracer.buildSpan("Productos creados").start();
		try{
			gProductos.guardarProductos(producto);
			ErrorMessage error = new ErrorMessage(1, "ok");
			return Response.status(Response.Status.CREATED).entity(error).build();
		}catch (Exception e) {
			// TODO: handle exception
			ErrorMessage error = new ErrorMessage(99, e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error)
					.build();
			
		} finally {
			span.finish();
		}
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Producto producto) {
		Span span = tracer.buildSpan("Actualizar Producto").start();
		try{
			gProductos.actualizarProducto(producto);
			return Response.ok(producto).build();
		}catch (Exception e) {
			// TODO: handle exception
			ErrorMessage error = new ErrorMessage(99, e.getMessage());
			return Response.status(Response.Status.NOT_FOUND)
					.entity(error)
					.build();
		} finally {
			span.finish();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrar(@QueryParam("id") int codigo) {
		Span span = tracer.buildSpan("Eliminar Producto").start();
	    try {
	    	gProductos.borrarProducto(codigo);
	        return Response.ok("Producto Eliminado").build();
	    } catch (Exception e) {
	        ErrorMessage error = new ErrorMessage(99, "Error al eliminar el Producto: " + e.getMessage());
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity(error)
	                .build();
	    }finally {
			span.finish();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	public Response getProductos(){
		List<Producto> productos = gProductos.getProductos();
		if(productos.size()>0)
			return Response.ok(productos).build();
		
		ErrorMessage error = new ErrorMessage(6, "No se registran Producto");
		return Response.status(Response.Status.NOT_FOUND)
				.entity(error)
				.build();
	}
	
}
