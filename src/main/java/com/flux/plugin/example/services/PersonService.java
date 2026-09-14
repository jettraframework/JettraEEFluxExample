package com.flux.plugin.example.services;

import com.flux.plugin.example.entity.Person;
import com.flux.plugin.example.repository.PersonRepository;
import com.flux.plugin.example.repository.PersonRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PersonService {

    @Inject
    private PersonRepository personRepository;

    private PersonRepository getRepo() {
        if (personRepository == null) {
            personRepository = new PersonRepositoryImpl();
        }
        return personRepository;
    }

    public List<Person> findAll() {
        return getRepo().findAll();
    }

    public List<Person> findAll(int page, int size) {
        return getRepo().findAll(page, size);
    }

    public void save(Person record) {
        getRepo().save(record);
    }

    public void delete(String id) {
        getRepo().delete(id);
    }

    public List<Person> findByName(String name) {
        return getRepo().findByName(name);
    }

    public List<Person> findByEmail(String email) {
        return getRepo().findByEmail(email);
    }

    public List<Person> findByAge(Integer age) {
        return getRepo().findByAge(age);
    }
}
