package net.mikoto.pixiv.database.dao.impl;

import net.mikoto.pixiv.api.pojo.IndexData;
import net.mikoto.pixiv.api.pojo.IndexType;
import net.mikoto.pixiv.api.pojo.PixivData;
import net.mikoto.pixiv.database.dao.BaseDao;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.mikoto.pixiv.database.PixivDatabaseApplication.PIXIV_DATA_DAO;

/**
 * @author mikoto
 * @date 2022/2/7 5:18
 */
public class PixivDataDao extends BaseDao {
    /**
     * Constants.
     */
    private static final String RESULT = "result";
    private static final Integer INDEX_MAX_PAGE = 5;

    private final ArrayList<IndexData> bookmarkCountIndexDataArrayList = new ArrayList<>();
    private final ArrayList<IndexData> likeCountIndexDataArrayList = new ArrayList<>();
    private final ArrayList<IndexData> viewCountIndexDataArrayList = new ArrayList<>();

    public PixivDataDao(String url, String userName, String password) {
        super(url, userName, password);
    }

    public void initIndexDataMap() throws SQLException, UnknownServiceTypeException {
        for (IndexType indexType :
                IndexType.values()) {
            for (int i = 1; i <= INDEX_MAX_PAGE; i++) {
                IndexData indexData = new IndexData();

                indexData.setIndexType(indexType);
                indexData.setTableName(indexType + "_index_" + i);

                // Get size
                String sql = "SELECT count(*) FROM pixiv_data." + indexData.getTableName() + ";";
                ResultSet resultSet = PIXIV_DATA_DAO.executeQuery(sql);
                if (resultSet.next()) {
                    indexData.setSize(resultSet.getInt("count(*)"));
                }

                // Get smallest
                sql = "SELECT * FROM pixiv_data." + indexData.getTableName() + " ORDER BY " + indexType + " ASC;";
                resultSet = PIXIV_DATA_DAO.executeQuery(sql);
                if (resultSet.next()) {
                    indexData.setSmallest(resultSet.getInt(String.valueOf(indexType)));
                }

                // Save index data
                switch (indexType) {
                    case bookmark_count -> bookmarkCountIndexDataArrayList.add(indexData);
                    case like_count -> likeCountIndexDataArrayList.add(indexData);
                    case view_count -> viewCountIndexDataArrayList.add(indexData);
                    default -> throw new UnknownServiceTypeException();
                }
            }
        }
    }

    /**
     * Query pixiv data from database.
     *
     * @param sql Sql statement
     * @return A pixiv data object.
     * @throws SQLException A sql error.
     */
    public PixivData queryPixivData(String sql) throws SQLException {
        ResultSet resultSet = executeQuery(sql);
        PixivData pixivData = new PixivData();

        if (resultSet.next()) {
            pixivData.setArtworkId(resultSet.getInt("pk_artwork_id"));
            pixivData.setArtworkTitle(resultSet.getString("artwork_title"));
            pixivData.setAuthorId(resultSet.getInt("author_id"));
            pixivData.setAuthorName(resultSet.getString("author_name"));
            pixivData.setDescription(resultSet.getString("description"));
            pixivData.setPageCount(resultSet.getInt("page_count"));
            pixivData.setBookmarkCount(resultSet.getInt("bookmark_count"));
            pixivData.setLikeCount(resultSet.getInt("like_count"));
            pixivData.setViewCount(resultSet.getInt("view_count"));
            pixivData.setGrading(resultSet.getInt("grading"));
            pixivData.setCrawlDate(resultSet.getDate("crawl_date").toString());
            pixivData.setCreateDate(resultSet.getDate("create_date").toString());
            pixivData.setUpdateDate(resultSet.getDate("update_date").toString());
            pixivData.setTags(resultSet.getString("tags").split(";"));
            Map<String, String> illustUrls = new HashMap<>(5);
            illustUrls.put("mini", resultSet.getString("illust_url_mini"));
            illustUrls.put("thumb", resultSet.getString("illust_url_thumb"));
            illustUrls.put("small", resultSet.getString("illust_url_small"));
            illustUrls.put("regular", resultSet.getString("illust_url_regular"));
            illustUrls.put("original", resultSet.getString("illust_url_original"));
            pixivData.setIllustUrls(illustUrls);
        }

        return pixivData;
    }

    /**
     * Insert pixiv data
     *
     * @param pixivData Pixiv data.
     * @throws SQLException ERROR.
     */
    public void insertPixivData(@NotNull PixivData pixivData) throws SQLException {
        Connection connection = getConnection();
        String sql = "select count(*) as result from pixiv_data." + getPixivDataTable(pixivData.getArtworkId()) + " where pk_artwork_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(RESULT) != 0) {
            sql = "DELETE FROM pixiv_data." + getPixivDataTable(pixivData.getArtworkId()) + " WHERE pk_artwork_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pixivData.getArtworkId());
            preparedStatement.executeUpdate();
        }

        sql = "INSERT INTO pixiv_data." + getPixivDataTable(pixivData.getArtworkId()) + " (pk_artwork_id, artwork_title, author_id, author_name, description, tags, illust_url_mini, illust_url_thumb, illust_url_small, illust_url_regular, illust_url_original, page_count, bookmark_count, like_count, view_count, grading, update_date, create_date, crawl_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        preparedStatement.setString(2, pixivData.getArtworkTitle());
        preparedStatement.setInt(3, pixivData.getAuthorId());
        preparedStatement.setString(4, pixivData.getAuthorName());
        preparedStatement.setString(5, pixivData.getDescription());
        preparedStatement.setString(6, getTagsString(pixivData.getTags()));
        preparedStatement.setString(7, pixivData.getIllustUrls().get("mini"));
        preparedStatement.setString(8, pixivData.getIllustUrls().get("thumb"));
        preparedStatement.setString(9, pixivData.getIllustUrls().get("small"));
        preparedStatement.setString(10, pixivData.getIllustUrls().get("regular"));
        preparedStatement.setString(11, pixivData.getIllustUrls().get("original"));
        preparedStatement.setInt(12, pixivData.getPageCount());
        preparedStatement.setInt(13, pixivData.getBookmarkCount());
        preparedStatement.setInt(14, pixivData.getLikeCount());
        preparedStatement.setInt(15, pixivData.getViewCount());
        preparedStatement.setInt(16, pixivData.getGrading());
        preparedStatement.setString(17, pixivData.getUpdateDate());
        preparedStatement.setString(18, pixivData.getCreateDate());
        preparedStatement.setString(19, pixivData.getCrawlDate());
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void insertIndex(@NotNull IndexType indexType, PixivData pixivData) throws UnknownServiceTypeException, SQLException {
        String indexTable = getIndexTable(indexType, pixivData);
        Connection connection = getConnection();

        String sql = "select count(*) as result from pixiv_data." + indexTable + " where pk_artwork_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(RESULT) != 0) {
            sql = "DELETE FROM pixiv_data." + indexTable + " WHERE pk_artwork_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pixivData.getArtworkId());
            preparedStatement.executeUpdate();
        }


        sql = "INSERT INTO pixiv_data." + indexTable + " (pk_artwork_id, " + indexType + ", tags, author_name) VALUES (?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        preparedStatement.setInt(2, pixivData.getBookmarkCount());
        preparedStatement.setString(3, getTagsString(pixivData.getTags()));
        preparedStatement.setString(4, pixivData.getAuthorName());
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    private @NotNull String getTagsString(@NotNull String @NotNull [] rawTags) {
        StringBuilder tags = new StringBuilder();
        for (int i = 0; i < rawTags.length; i++) {
            tags.append(rawTags[i]);
            if (i != tags.length() - 1) {
                tags.append(";");
            }
        }
        return tags.toString();
    }

    /**
     * Get table.
     *
     * @param artworkId The id of this artwork.
     * @return A table name.
     */
    public String getPixivDataTable(@NotNull Integer artworkId) {
        return "artwork_table_" + (int) Math.ceil(Double.valueOf(artworkId) / 5000000.0);
    }

    public synchronized String getIndexTable(@NotNull IndexType indexType, @NotNull PixivData pixivData) throws UnknownServiceTypeException, SQLException {
        Connection connection = getConnection();
        String sql;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<IndexData> indexDataArrayList;
        int data;
        switch (indexType) {
            case bookmark_count -> {
                indexDataArrayList = bookmarkCountIndexDataArrayList;
                data = pixivData.getBookmarkCount();
            }
            case like_count -> {
                indexDataArrayList = likeCountIndexDataArrayList;
                data = pixivData.getLikeCount();
            }
            case view_count -> {
                indexDataArrayList = viewCountIndexDataArrayList;
                data = pixivData.getViewCount();
            }
            default -> throw new UnknownServiceTypeException();
        }
        for (IndexData indexData :
                indexDataArrayList) {
            if (indexData.getSize() < 20000000.0) {
                updateIndexData(indexType, connection, indexData);
                return indexData.getTableName();
            } else {
                if (data > indexData.getSmallest()) {
                    sql = "SELECT * from pixiv_data." + indexData.getTableName() + " ORDER BY " + indexType + " ASC;";
                    preparedStatement = connection.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();

                    PixivData movedPixivData = new PixivData();
                    if (resultSet.next()) {
                        movedPixivData.setArtworkId(resultSet.getInt("pk_artwork_id"));
                        movedPixivData.setBookmarkCount(resultSet.getInt(String.valueOf(indexType)));
                        movedPixivData.setLikeCount(resultSet.getInt(String.valueOf(indexType)));
                        movedPixivData.setViewCount(resultSet.getInt(String.valueOf(indexType)));
                        movedPixivData.setTags(resultSet.getString("tags").split(";"));
                        movedPixivData.setAuthorName(resultSet.getString("author_name"));
                    }
                    updateIndexData(indexType, connection, indexData);

                    insertIndex(indexType, movedPixivData);

                    sql = "DELETE FROM pixiv_data." + indexData.getTableName() + " WHERE pk_artwork_id=?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, movedPixivData.getArtworkId());
                    preparedStatement.executeUpdate();
                    return indexData.getTableName();
                }
            }
        }
        return null;
    }

    private void updateIndexData(@NotNull IndexType indexType, @NotNull Connection connection, @NotNull IndexData indexData) throws SQLException {
        String sql;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        indexData.setSize(indexData.getSize() + 1);
        sql = "SELECT * from pixiv_data." + indexData.getTableName() + " ORDER BY " + indexType + " ASC;";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            indexData.setSmallest(resultSet.getInt(String.valueOf(indexType)));
        }
    }
}
