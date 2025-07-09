package application.dto;

import lombok.Data;

@Data
public class NewPostRequest {
    private Long authorId;
    private String content;
}