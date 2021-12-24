package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        candidates.put(1, new Candidate(1, "Mark"));
        candidates.put(2, new Candidate(2, "Jacob"));
        candidates.put(3, new Candidate(3, "Larry"));
        posts.put(1, new Post(1, "Junior Java"));
        posts.put(2, new Post(2, "Middle Java"));
        posts.put(3, new Post(3, "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    public boolean deleteByIdCandidate(int id) {
        Candidate candidate = candidates.remove(id);
        return candidate != null;
    }
}