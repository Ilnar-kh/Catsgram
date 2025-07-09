package application.service;

import org.springframework.stereotype.Service;
import dal.PostRepository;
import application.dto.NewPostRequest;
import application.dto.PostDto;
import application.dto.UpdatePostRequest;
import exception.NotFoundException;
import application.mapper.PostMapper;
import model.Post;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public PostDto create(NewPostRequest request) {
        Post post = PostMapper.toModel(request);
        post = repository.save(post);
        return PostMapper.toDto(post);
    }

    public PostDto getById(long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found: " + id));
        return PostMapper.toDto(post);
    }

    public List<PostDto> getAll() {
        return repository.findAll().stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getByAuthor(long authorId) {
        return repository.findByAuthorId(authorId).stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDto update(long id, UpdatePostRequest request) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found: " + id));
        post = PostMapper.applyPatch(post, request);
        post = repository.update(post);
        return PostMapper.toDto(post);
    }

    public void delete(long id) {
        boolean removed = repository.delete(id);
        if (!removed) {
            throw new NotFoundException("Post not found: " + id);
        }
    }
}