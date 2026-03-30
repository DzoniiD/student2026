package me.fit.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import me.fit.entity.Product;

import java.util.List;

@ApplicationScoped
public class ProductScheduler {

    @Inject
    EntityManager em;

    @Scheduled(every = "60s")
    void checkLowStock() {
        List<Product> products = em.createQuery(
                "select p from Product p where p.stock < 5", Product.class
        ).getResultList();

        System.out.println("Broj proizvoda sa malim lagerom: " + products.size());
    }
}
