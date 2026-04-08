package me.fit.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import me.fit.entity.Employee;

import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EntityManager em;

    @POST
    @Transactional
    public Employee add(Employee employee) {
        em.persist(employee);
        return employee;
    }

    @GET
    public List<Employee> getAll() {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @GET
    @Path("/admin-only")
    @RolesAllowed("admin")
    public String adminOnly() {
        return "Pristup dozvoljen samo admin korisniku.";
    }
}
