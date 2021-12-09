package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        try {
            posts.put(1, new Post(1, "Mark", "@mdo", format.parse("01.02.2020")));
            posts.put(2, new Post(2, "Jacob", "@fat", format.parse("10.04.2020")));
            posts.put(3, new Post(3, "Larry", "@twitter", format.parse("12.07.2020")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}