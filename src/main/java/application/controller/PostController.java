package application.controller;

import application.dto.NewPostRequest;
import application.dto.PostDto;
import application.dto.UpdatePostRequest;
import application.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@RequestBody NewPostRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<PostDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/author/{authorId}")
    public List<PostDto> getByAuthor(@PathVariable long authorId) {
        return service.getByAuthor(authorId);
    }

    @GetMapping("/{id}")
    public PostDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable long id,
                          @RequestBody UpdatePostRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
