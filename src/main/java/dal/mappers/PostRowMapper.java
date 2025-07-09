package dal.mappers;

import model.Post;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PostRowMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getLong("id"));
        post.setAuthorId(resultSet.getLong("author_id"));
        post.setContent(resultSet.getString("content"));
        post.setCreatedDate(resultSet.getTimestamp("created_date").toInstant());
        return post;
    }
}
