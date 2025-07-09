package application.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class ImageDto {
    private Long id;
    private Long postId;
    private String url;
    private Instant uploadDate;
}
