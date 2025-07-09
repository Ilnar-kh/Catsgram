package application.dto;

import lombok.Data;

@Data
public class NewImageRequest {
    private Long postId;
    private String url;
}