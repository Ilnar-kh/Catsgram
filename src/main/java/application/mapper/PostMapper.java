package application.mapper;

import lombok.NoArgsConstructor;
import application.dto.NewPostRequest;
import application.dto.PostDto;
import application.dto.UpdatePostRequest;
import model.Post;

import java.time.Instant;

@NoArgsConstructor
public final class PostMapper {
    public static Post toModel(NewPostRequest r) {
        Post post = new Post();
        post.setAuthorId(r.getAuthorId());
        post.setContent(r.getContent());
        post.setCreatedDate(Instant.now());
        return post;
    }

    public static PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setAuthorId(post.getAuthorId());
        dto.setContent(post.getContent());
        dto.setCreatedDate(post.getCreatedDate());
        return dto;
    }

    public static Post applyPatch(Post post, UpdatePostRequest request) {
        if (request.hasContent()) {
            post.setContent(request.getContent());
        }
        return post;
    }
}
