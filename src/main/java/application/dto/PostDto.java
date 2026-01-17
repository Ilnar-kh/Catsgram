package application.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PostDto {
    private Long id;
    private Long authorId;
    private String content;
    private Instant createdDate;
}
