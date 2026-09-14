package com.flux.plugin.example.controller;

import com.flux.plugin.example.entity.Person;
import com.flux.plugin.example.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@ApplicationScoped
@Path("/api/persons")
@Tag(name = "Person", description = "API for Person management")
public class PersonController {

    @Inject
    private PersonRepository personRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "findAll", description = "Returns all records")
    public List<Person> findAll(@QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        if (personRepository == null) return List.of();
        if (page != null && size != null) {
            return personRepository.findAll(page, size);
        }
        return personRepository.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "save", description = "Saves a new Person")
    public Response save(Person record) {
        if (personRepository != null) {
            personRepository.save(record);
        }
        return Response.ok("{\"message\": \"Saved successfully\"}").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "update", description = "Updates an existing Person")
    public Response update(Person record) {
        if (personRepository != null) {
            personRepository.save(record);
        }
        return Response.ok("{\"message\": \"Updated successfully\"}").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "delete", description = "Deletes a Person by id")
    public Response delete(@PathParam("id") String id) {
        if (personRepository != null) {
            personRepository.delete(id);
        }
        return Response.ok("{\"message\": \"Deleted successfully\"}").build();
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "findByName", description = "Finds records by name")
    public List<Person> findByName(@PathParam("name") String name) {
        if (personRepository == null) return List.of();
        return personRepository.findByName(name);
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "findByEmail", description = "Finds records by email")
    public List<Person> findByEmail(@PathParam("email") String email) {
        if (personRepository == null) return List.of();
        return personRepository.findByEmail(email);
    }

    @GET
    @Path("/age/{age}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "findByAge", description = "Finds records by age")
    public List<Person> findByAge(@PathParam("age") Integer age) {
        if (personRepository == null) return List.of();
        return personRepository.findByAge(age);
    }
}
