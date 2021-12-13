package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
            posts.put(1, new Post(1, "Mark", "@mdo", new GregorianCalendar(2021, 10, 10).getTime()));
            posts.put(2, new Post(2, "Jacob", "@fat", new GregorianCalendar(2021, 01, 12).getTime()));
            posts.put(3, new Post(3, "Larry", "@twitter", new GregorianCalendar(2021, 03, 8).getTime()));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}