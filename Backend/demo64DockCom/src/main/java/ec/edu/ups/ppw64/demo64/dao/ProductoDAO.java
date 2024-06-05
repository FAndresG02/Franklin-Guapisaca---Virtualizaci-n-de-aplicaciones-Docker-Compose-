package ec.edu.ups.ppw64.demo64.dao;

import java.util.List;

import ec.edu.ups.ppw64.demo64.model.Producto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ProductoDAO {

	@PersistenceContext
    private EntityManager em;

	public void insert(Producto producto) {
        producto = em.merge(producto);
        em.persist(producto);
    }
    
    public void update(Producto producto) {
        em.merge(producto);
    }
    
    public void remove(int codigo) {
        Producto producto = em.find(Producto.class, codigo);
        em.remove(producto);
    }
    
    public Producto read(int codigo) {
        Producto producto = em.find(Producto.class, codigo);
        return producto;
    }
    
    public List<Producto> getAll() {
        String jpql = "SELECT c FROM Producto c";
        Query q = em.createQuery(jpql, Producto.class);
        return q.getResultList();
    }
    
    public Producto getProductoPorNombre(String nombre) {
        String jpql = "SELECT c FROM Producto c WHERE c.nombre = :nombre";
        Query q = em.createQuery(jpql, Producto.class);
        q.setParameter("nombre", nombre);
        List<Producto> producto = q.getResultList();
        if (producto.size() > 0)
            return producto.get(0);
        return null;
    }
}
