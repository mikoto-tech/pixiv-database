package net.mikoto.pixiv.database.dao.impl;

import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.api.pojo.IndexData;
import net.mikoto.pixiv.api.pojo.IndexType;
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

import static net.mikoto.pixiv.database.PixivDatabaseApplication.ARTWORK_DAO;

/**
 * @author mikoto
 * @date 2022/2/7 5:18
 */
public class ArtworkDao extends BaseDao {
    /**
     * Constants.
     */
    private static final String RESULT = "result";
    private static final Integer INDEX_MAX_PAGE = 5;

    private final ArrayList<IndexData> bookmarkCountIndexDataArrayList = new ArrayList<>();
    private final ArrayList<IndexData> likeCountIndexDataArrayList = new ArrayList<>();
    private final ArrayList<IndexData> viewCountIndexDataArrayList = new ArrayList<>();

    public ArtworkDao(String url, String userName, String password) {
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
                ResultSet resultSet = ARTWORK_DAO.executeQuery(sql);
                if (resultSet.next()) {
                    indexData.setSize(resultSet.getInt("count(*)"));
                }

                // Get smallest
                sql = "SELECT * FROM pixiv_data." + indexData.getTableName() + " ORDER BY " + indexType + " ASC;";
                resultSet = ARTWORK_DAO.executeQuery(sql);
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
    public Artwork queryArtwork(String sql) throws SQLException {
        ResultSet resultSet = executeQuery(sql);
        Artwork artwork = new Artwork();

        if (resultSet.next()) {
            artwork.setArtworkId(resultSet.getInt("pk_artwork_id"));
            artwork.setArtworkTitle(resultSet.getString("artwork_title"));
            artwork.setAuthorId(resultSet.getInt("author_id"));
            artwork.setAuthorName(resultSet.getString("author_name"));
            artwork.setDescription(resultSet.getString("description"));
            artwork.setPageCount(resultSet.getInt("page_count"));
            artwork.setBookmarkCount(resultSet.getInt("bookmark_count"));
            artwork.setLikeCount(resultSet.getInt("like_count"));
            artwork.setViewCount(resultSet.getInt("view_count"));
            artwork.setGrading(resultSet.getInt("grading"));
            artwork.setCrawlDate(resultSet.getDate("crawl_date").toString());
            artwork.setCreateDate(resultSet.getDate("create_date").toString());
            artwork.setUpdateDate(resultSet.getDate("update_date").toString());
            artwork.setTags(resultSet.getString("tags").split(";"));
            Map<String, String> illustUrls = new HashMap<>(5);
            illustUrls.put("mini", resultSet.getString("illust_url_mini"));
            illustUrls.put("thumb", resultSet.getString("illust_url_thumb"));
            illustUrls.put("small", resultSet.getString("illust_url_small"));
            illustUrls.put("regular", resultSet.getString("illust_url_regular"));
            illustUrls.put("original", resultSet.getString("illust_url_original"));
            artwork.setIllustUrls(illustUrls);
        }

        return artwork;
    }

    /**
     * Insert pixiv data
     *
     * @param artwork Pixiv data.
     * @throws SQLException ERROR.
     */
    public void insertArtwork(@NotNull Artwork artwork) throws SQLException {
        Connection connection = getConnection();
        String sql = "select count(*) as result from pixiv_data." + getArtworkTable(artwork.getArtworkId()) + " where pk_artwork_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, artwork.getArtworkId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(RESULT) != 0) {
            sql = "DELETE FROM pixiv_data." + getArtworkTable(artwork.getArtworkId()) + " WHERE pk_artwork_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, artwork.getArtworkId());
            preparedStatement.executeUpdate();
        }

        sql = "INSERT INTO pixiv_data." + getArtworkTable(artwork.getArtworkId()) + " (pk_artwork_id, artwork_title, author_id, author_name, description, tags, illust_url_mini, illust_url_thumb, illust_url_small, illust_url_regular, illust_url_original, page_count, bookmark_count, like_count, view_count, grading, update_date, create_date, crawl_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, artwork.getArtworkId());
        preparedStatement.setString(2, artwork.getArtworkTitle());
        preparedStatement.setInt(3, artwork.getAuthorId());
        preparedStatement.setString(4, artwork.getAuthorName());
        preparedStatement.setString(5, artwork.getDescription());
        preparedStatement.setString(6, getTagsString(artwork.getTags()));
        preparedStatement.setString(7, artwork.getIllustUrls().get("mini"));
        preparedStatement.setString(8, artwork.getIllustUrls().get("thumb"));
        preparedStatement.setString(9, artwork.getIllustUrls().get("small"));
        preparedStatement.setString(10, artwork.getIllustUrls().get("regular"));
        preparedStatement.setString(11, artwork.getIllustUrls().get("original"));
        preparedStatement.setInt(12, artwork.getPageCount());
        preparedStatement.setInt(13, artwork.getBookmarkCount());
        preparedStatement.setInt(14, artwork.getLikeCount());
        preparedStatement.setInt(15, artwork.getViewCount());
        preparedStatement.setInt(16, artwork.getGrading());
        preparedStatement.setString(17, artwork.getUpdateDate());
        preparedStatement.setString(18, artwork.getCreateDate());
        preparedStatement.setString(19, artwork.getCrawlDate());
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void insertIndex(@NotNull IndexType indexType, Artwork artwork) throws UnknownServiceTypeException, SQLException {
        String indexTable = getIndexTable(indexType, artwork);
        Connection connection = getConnection();

        String sql = "select count(*) as result from pixiv_data." + indexTable + " where pk_artwork_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, artwork.getArtworkId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(RESULT) != 0) {
            sql = "DELETE FROM pixiv_data." + indexTable + " WHERE pk_artwork_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, artwork.getArtworkId());
            preparedStatement.executeUpdate();
        }


        sql = "INSERT INTO pixiv_data." + indexTable + " (pk_artwork_id, " + indexType + ", tags, author_name) VALUES (?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, artwork.getArtworkId());
        preparedStatement.setInt(2, artwork.getBookmarkCount());
        preparedStatement.setString(3, getTagsString(artwork.getTags()));
        preparedStatement.setString(4, artwork.getAuthorName());
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
    public String getArtworkTable(@NotNull Integer artworkId) {
        return "artwork_table_" + (int) Math.ceil(Double.valueOf(artworkId) / 5000000.0);
    }

    public synchronized String getIndexTable(@NotNull IndexType indexType, @NotNull Artwork artwork) throws UnknownServiceTypeException, SQLException {
        Connection connection = getConnection();
        String sql;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<IndexData> indexDataArrayList;
        int data;
        switch (indexType) {
            case bookmark_count -> {
                indexDataArrayList = bookmarkCountIndexDataArrayList;
                data = artwork.getBookmarkCount();
            }
            case like_count -> {
                indexDataArrayList = likeCountIndexDataArrayList;
                data = artwork.getLikeCount();
            }
            case view_count -> {
                indexDataArrayList = viewCountIndexDataArrayList;
                data = artwork.getViewCount();
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

                    Artwork movedArtwork = new Artwork();
                    if (resultSet.next()) {
                        movedArtwork.setArtworkId(resultSet.getInt("pk_artwork_id"));
                        movedArtwork.setBookmarkCount(resultSet.getInt(String.valueOf(indexType)));
                        movedArtwork.setLikeCount(resultSet.getInt(String.valueOf(indexType)));
                        movedArtwork.setViewCount(resultSet.getInt(String.valueOf(indexType)));
                        movedArtwork.setTags(resultSet.getString("tags").split(";"));
                        movedArtwork.setAuthorName(resultSet.getString("author_name"));
                    }
                    updateIndexData(indexType, connection, indexData);

                    insertIndex(indexType, movedArtwork);

                    sql = "DELETE FROM pixiv_data." + indexData.getTableName() + " WHERE pk_artwork_id=?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, movedArtwork.getArtworkId());
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
