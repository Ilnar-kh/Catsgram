package application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import application.dto.*;
import application.service.ImageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDto create(@RequestBody NewImageRequest request) {
        return service.create(request);
    }

    @GetMapping("/post/{postId}")
    public List<ImageDto> getByPost(@PathVariable long postId) {
        return service.getByPost(postId);
    }

    @GetMapping("/{id}")
    public ImageDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ImageDto update(@PathVariable long id,
                           @RequestBody UpdateImageRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
