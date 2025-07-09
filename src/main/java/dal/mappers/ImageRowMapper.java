package dal.mappers;

import model.Image;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ImageRowMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Image image = new Image();
        image.setId(resultSet.getLong("id"));
        image.setPostId(resultSet.getLong("post_id"));
        image.setUrl(resultSet.getString("url"));
        image.setUploadDate(resultSet.getTimestamp("upload_date").toInstant());
        return image;
    }
}