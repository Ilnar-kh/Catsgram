package application.dto;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String content;

    public boolean hasContent() {
        return content != null && !content.isBlank();
    }
}

