package com.flux.plugin.example.repository;

import com.flux.plugin.example.entity.Person;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PersonRepositoryImpl implements PersonRepository {

    private static final List<Person> db = new ArrayList<>();

    static {
        db.add(new Person("Alice Johnson", "alice@jettra.io", 28));
        db.add(new Person("Bob Smith", "bob@jettra.io", 34));
        db.add(new Person("Carlos Gomez", "carlos@jettra.io", 41));
        db.add(new Person("Diana Prince", "diana@jettra.io", 29));
    }

    @Override
    public List<Person> findAll() {
        synchronized (db) {
            return new ArrayList<>(db);
        }
    }

    @Override
    public List<Person> findAll(int page, int size) {
        synchronized (db) {
            int from = (page - 1) * size;
            if (from >= db.size()) return new ArrayList<>();
            int to = Math.min(from + size, db.size());
            return new ArrayList<>(db.subList(from, to));
        }
    }

    @Override
    public void save(Person record) {
        synchronized (db) {
            delete(record.name());
            db.add(record);
        }
    }

    @Override
    public void delete(String id) {
        synchronized (db) {
            db.removeIf(r -> r.name().equals(id));
        }
    }

    @Override
    public Optional<Person> findById(String id) {
        synchronized (db) {
            return db.stream().filter(r -> r.name().equals(id)).findFirst();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        synchronized (db) {
            return db.stream().filter(r -> r.name().equalsIgnoreCase(name)).collect(Collectors.toList());
        }
    }

    @Override
    public List<Person> findByEmail(String email) {
        synchronized (db) {
            return db.stream().filter(r -> r.email().equalsIgnoreCase(email)).collect(Collectors.toList());
        }
    }

    @Override
    public List<Person> findByAge(Integer age) {
        synchronized (db) {
            return db.stream().filter(r -> r.age().equals(age)).collect(Collectors.toList());
        }
    }
}
