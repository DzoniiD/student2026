package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.entity.Customer;
import me.fit.entity.CustomerOrder;
import me.fit.entity.OrderItem;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    EntityManager em;

    @POST
    @Transactional
    public Response add(Customer customer) {
        if (customer.orders != null) {
            for (CustomerOrder order : customer.orders) {
                order.customer = customer;

                if (order.items != null) {
                    for (OrderItem item : order.items) {
                        item.order = order;
                    }
                }
            }
        }

        em.persist(customer);
        return Response.ok(customer).build();
    }

    @GET
    public List<Customer> getAll() {
        return em.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }

    @GET
    @Path("/{id}/orders")
    @Transactional
    public List<CustomerOrder> getOrdersForCustomer(@PathParam("id") Long id) {
        return em.createQuery(
                "select o from CustomerOrder o where o.customer.id = :id",
                CustomerOrder.class
        ).setParameter("id", id).getResultList();
    }
}