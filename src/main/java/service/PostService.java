package service;

import exception.ConditionsNotMetException;
import exception.NotFoundException;
import model.Post;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.Instant;
import java.util.*;

// Указываем, что класс PostService - является бином и его
// нужно добавить в контекст приложения
@Service
public class PostService {
    private final Map<Long, Post> posts = new HashMap<>();
    private final Comparator<Post> postDateComparator = Comparator.comparing(Post::getPostDate);
    private final UserService userService;

    // Внедряем UserService через конструктор
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public Collection<Post> findAll(SortOrder sort, int from, int size) {
        return posts.values()
                .stream()
                .sorted(sort.equals(SortOrder.ASCENDING) ?
                        postDateComparator : postDateComparator.reversed())
                .skip(from)
                .limit(size)
                .toList();
    }

    public Post create(Post post) {
        if (post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        Long authorId = post.getAuthorId();
        if (userService.findUserById(authorId).isEmpty()) {
            throw new ConditionsNotMetException("Автор с id = " + authorId + " не найден");
        }

        post.setId(getNextId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(Post newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (!posts.containsKey(newPost.getId())) {
            throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
        }

        if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        Post old = posts.get(newPost.getId());
        old.setDescription(newPost.getDescription());
        return old;
    }

    private long getNextId() {
        long currentMaxId = posts.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public Optional<Post> findById(long postId) {
        return Optional.ofNullable(posts.get(postId));
    }
}