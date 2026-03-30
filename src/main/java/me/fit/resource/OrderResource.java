package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import me.fit.entity.CustomerOrder;
import me.fit.entity.OrderItem;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    EntityManager em;

    @GET
    @Path("/{id}/items")
    @Transactional
    public List<OrderItem> getItemsForOrder(@PathParam("id") Long id) {
        return em.createQuery(
                "select i from OrderItem i where i.order.id = :id",
                OrderItem.class
        ).setParameter("id", id).getResultList();
    }
}