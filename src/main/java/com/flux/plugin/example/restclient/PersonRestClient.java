package com.flux.plugin.example.restclient;

import com.flux.plugin.example.entity.Person;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@RegisterRestClient(baseUri = "http://localhost:8080/api/persons")
@Path("/api/persons")
public interface PersonRestClient {

    @GET
    List<Person> findAll();

    @POST
    void save(Person person);

    @PUT
    void update(Person person);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") String id);

    @GET
    @Path("/name/{name}")
    List<Person> findByName(@PathParam("name") String name);

    @GET
    @Path("/email/{email}")
    List<Person> findByEmail(@PathParam("email") String email);

    @GET
    @Path("/age/{age}")
    List<Person> findByAge(@PathParam("age") Integer age);
}
