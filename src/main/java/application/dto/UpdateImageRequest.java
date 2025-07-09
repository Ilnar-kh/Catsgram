package application.dto;

import lombok.Data;

@Data
public class UpdateImageRequest {
    private String url;

    public boolean hasUrl() {
        return url != null && !url.isBlank();
    }
}