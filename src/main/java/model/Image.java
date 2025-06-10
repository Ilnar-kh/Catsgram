package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"id"})
public @Data class Image {
    private Long id;
    private long postId;
    private String originalFileName;
    private  String filePath;
}
