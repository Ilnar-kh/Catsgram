package application.service;


import application.dto.ImageDto;
import application.dto.NewImageRequest;
import application.dto.UpdateImageRequest;
import application.mapper.ImageMapper;
import dal.ImageStorageRepository;
import exception.NotFoundException;
import model.Image;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageStorageRepository repository;

    public ImageService(ImageStorageRepository repository) {
        this.repository = repository;
    }

    public ImageDto create(NewImageRequest request) {
        Image image = ImageMapper.toModel(request);
        image = repository.save(image);
        return ImageMapper.toDto(image);
    }

    public ImageDto getById(long id) {
        Image image = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found: " + id));
        return ImageMapper.toDto(image);
    }

    public List<ImageDto> getByPost(long postId) {
        return repository.findByPostId(postId).stream()
                .map(ImageMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        boolean removed = repository.delete(id);
        if (!removed) throw new NotFoundException("Image not found: " + id);
    }

    public ImageDto update(long id, UpdateImageRequest request) {
        Image image = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found: " + id));
        image = ImageMapper.applyPatch(image, request);
        image = repository.update(image);
        return ImageMapper.toDto(image);
    }
}