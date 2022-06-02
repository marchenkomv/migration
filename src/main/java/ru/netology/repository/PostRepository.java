package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {

    private ConcurrentMap<Long, Post> repository;
    private AtomicLong counter;
    private final long COUNTER_INITIAL_VALUE = 1;

    public PostRepository() {
        repository = new ConcurrentHashMap<>();
        counter = new AtomicLong(COUNTER_INITIAL_VALUE);
    }

    public List<Post> all() {
        return new ArrayList<>(repository.values()
                .stream()
                .filter(e -> !e.isRemoved())
                .collect(Collectors.toList()));
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(repository.containsKey(id) && !repository.get(id).isRemoved() ? repository.get(id) : null);
    }

    public Optional<Post> save(Post post) {
        if (post.getId() == 0L) {
            var key = counter.getAndIncrement();
            post.setId(key);
            repository.put(key, post);
        } else {
            if (repository.containsKey(post.getId()) && !repository.get(post.getId()).isRemoved()) {
                repository.get(post.getId()).setContent(post.getContent());
            } else {
                post = null;
            }
        }
        return Optional.ofNullable(post);
    }

    public Optional<Post> removeById(long id) {
        Post post = null;
        if (repository.containsKey(id)) {
            post = repository.get(id);
            if (!post.isRemoved()) {
                post.setRemoved(true);
            } else {
                post = null;
            }
        }
        return Optional.ofNullable(post);
    }
}