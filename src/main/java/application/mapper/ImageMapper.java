package application.mapper;

import lombok.NoArgsConstructor;
import application.dto.*;
import model.Image;

import java.time.Instant;

@NoArgsConstructor
public final class ImageMapper {
    public static Image toModel(NewImageRequest request) {
        Image image = new Image();
        image.setPostId(request.getPostId());
        image.setUrl(request.getUrl());
        image.setUploadDate(Instant.now());
        return image;
    }

    public static ImageDto toDto(Image image) {
        ImageDto dto = new ImageDto();
        dto.setId(image.getId());
        dto.setPostId(image.getPostId());
        dto.setUrl(image.getUrl());
        dto.setUploadDate(image.getUploadDate());
        return dto;
    }

    public static Image applyPatch(Image image, UpdateImageRequest request) {
        if (request.hasUrl()) {
            image.setUrl(request.getUrl());
        }
        return image;
    }
}
