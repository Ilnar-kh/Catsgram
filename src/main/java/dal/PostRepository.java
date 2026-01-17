package dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import model.Post;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository extends BaseRepository<Post> {
    private static final String FIND_ALL = "SELECT * FROM posts";
    private static final String FIND_BY_ID = "SELECT * FROM posts WHERE id = ?";
    private static final String FIND_BY_AUTHOR = "SELECT * FROM posts WHERE author_id = ?";
    private static final String INSERT =
            "INSERT INTO posts(author_id, content, created_date) VALUES (?, ?, ?) returning id";
    private static final String UPDATE =
            "UPDATE posts SET content = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM posts WHERE id = ?";

    public PostRepository(JdbcTemplate jdbc, RowMapper<Post> mapper) {
        super(jdbc, mapper);
    }

    public List<Post> findAll() {
        return findMany(FIND_ALL);
    }

    public Optional<Post> findById(long id) {
        return findOne(FIND_BY_ID, id);
    }

    public List<Post> findByAuthorId(long authorId) {
        return findMany(FIND_BY_AUTHOR, authorId);
    }

    public Post save(Post post) {
        long id = insert(
                INSERT,
                post.getAuthorId(),
                post.getContent(),
                Timestamp.from(post.getCreatedDate())
        );
        post.setId(id);
        return post;
    }

    public Post update(Post post) {
        update(UPDATE,
                post.getContent(),
                post.getId()
        );
        return post;
    }

    public boolean delete(long id) {
        return delete(DELETE, id);
    }
}