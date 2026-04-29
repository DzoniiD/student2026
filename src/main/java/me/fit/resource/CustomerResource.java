package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.dto.IpClient;
import me.fit.dto.IpResponse;
import me.fit.dto.TimeClient;
import me.fit.dto.TimezoneResponse;
import me.fit.entity.Customer;
import me.fit.entity.CustomerOrder;
import me.fit.entity.OrderItem;
import me.fit.entity.TimezoneInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    EntityManager em;

    @Inject
    @RestClient
    IpClient ipClient;

    @Inject
    @RestClient
    TimeClient timeClient;

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

    @GET
    @Path("/getTimezoneByIP")
    @Transactional
    public Response getTimezone(@QueryParam("userId") Long userId) {

        Customer customer = em.find(Customer.class, userId);


        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer with id " + userId + " not found")
                    .build();
        }

        try {

            IpResponse ipResponse = ipClient.getIp();
            String ipAddress = ipResponse.ip;

            System.out.println("IP Address: " + ipAddress);


            TimezoneResponse tzResponse = timeClient.getTime(ipAddress);

            System.out.println("Timezone: " + tzResponse.timeZone);
            System.out.println("Local Time: " + tzResponse.currentLocalTime);


            TimezoneInfo timezoneInfo = new TimezoneInfo();
            timezoneInfo.timeZone = tzResponse.timeZone;
            timezoneInfo.currentLocalTime = tzResponse.currentLocalTime;
            timezoneInfo.customer = customer;


            if (customer.timezones == null) {
                customer.timezones = new ArrayList<>();
            }
            customer.timezones.add(timezoneInfo);


            em.persist(timezoneInfo);
            em.merge(customer);


            return Response.ok(timezoneInfo).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to get timezone information: " + e.getMessage())
                    .build();
        }
    }
}