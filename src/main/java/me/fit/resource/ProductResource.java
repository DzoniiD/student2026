package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import me.fit.entity.Product;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    EntityManager em;

    @POST
    @Transactional
    public Product add(Product product) {
        em.persist(product);
        return product;
    }

    @GET
    public List<Product> getAll() {
        return em.createQuery("select p from Product p", Product.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Product findById(@PathParam("id") Long id) {
        return em.find(Product.class, id);
    }

    @GET
    @Path("/searchByName")
    public List<Product> findByName(@QueryParam("name") String name) {
        return em.createQuery(
                "select p from Product p where lower(p.name) like lower(concat('%', :name, '%'))",
                Product.class
        ).setParameter("name", name).getResultList();
    }
}
