package dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import model.Image;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class ImageStorageRepository extends BaseRepository<Image> {
    private static final String FIND_ALL = "SELECT * FROM image_storage";
    private static final String FIND_BY_ID = "SELECT * FROM image_storage WHERE id = ?";
    private static final String FIND_BY_POST = "SELECT * FROM image_storage WHERE post_id = ?";
    private static final String INSERT =
            "INSERT INTO image_storage(post_id, url, upload_date) VALUES (?, ?, ?) returning id";
    private static final String UPDATE =
            "UPDATE image_storage SET url = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM image_storage WHERE id = ?";

    public ImageStorageRepository(JdbcTemplate jdbc, RowMapper<Image> mapper) {
        super(jdbc, mapper);
    }

    public List<Image> findAll() {
        return findMany(FIND_ALL);
    }

    public Optional<Image> findById(long id) {
        return findOne(FIND_BY_ID, id);
    }

    public List<Image> findByPostId(long postId) {
        return findMany(FIND_BY_POST, postId);
    }

    public Image save(Image image) {
        long id = insert(
                INSERT,
                image.getPostId(),
                image.getUrl(),
                Timestamp.from(image.getUploadDate())
        );
        image.setId(id);
        return image;
    }

    public Image update(Image image) {
        update(UPDATE,
                image.getUrl(),
                image.getId()
        );
        return image;
    }

    public boolean delete(long id) {
        return delete(DELETE, id);
    }
}
