package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        candidates.put(1, new Candidate(1, "Mark"));
        candidates.put(2, new Candidate(2, "Jacob"));
        candidates.put(3, new Candidate(3, "Larry"));
        posts.put(1, new Post(1, "Junior Java"));
        posts.put(2, new Post(2, "Middle Java"));
        posts.put(3, new Post(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}