package application.controller;

import model.Post;
import model.SortOrder;
import org.springframework.web.bind.annotation.*;
import service.PostService;
import exception.ParameterNotValidException;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam(defaultValue = "desc") String sort,
                                    @RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        // проверяем sort
        SortOrder sortOrder = SortOrder.from(sort);
        if (sortOrder == null) {
            throw new ParameterNotValidException(
                    "sort",
                    "Получено: " + sort + ", должно быть: asc или desc"
            );
        }
        // проверяем size
        if (size <= 0) {
            throw new ParameterNotValidException(
                    "size",
                    "Размер должен быть больше нуля"
            );
        }
        // проверяем from
        if (from < 0) {
            throw new ParameterNotValidException(
                    "from",
                    "Начало выборки не может быть меньше нуля"
            );
        }

        return postService.findAll(sortOrder, from, size);
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable long postId) {
        return postService.findById(postId)
                .orElseThrow(() -> new exception.NotFoundException("Пост с id=" + postId + " не найден"));
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }
}
