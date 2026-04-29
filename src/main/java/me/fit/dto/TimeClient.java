package me.fit.dto;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/time/current/ip")
@RegisterRestClient(baseUri = "https://timeapi.io")
public interface TimeClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    TimezoneResponse getTime(@QueryParam("ipAddress") String ipAddress);
}
