package me.fit.dto;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(baseUri = "https://api.ipify.org")
public interface IpClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    IpResponse getIp();
}
