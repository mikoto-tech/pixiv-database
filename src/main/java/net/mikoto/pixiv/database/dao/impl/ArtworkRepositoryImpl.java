package net.mikoto.pixiv.database.dao.impl;

import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.database.dao.ArtworkCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
public class ArtworkRepositoryImpl implements ArtworkCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(String tableName, Artwork artwork) {
        // 插入主表
        //noinspection SqlInsertValues
        String sql = "INSERT INTO " + tableName +
                " (pk_artwork_id, artwork_title, author_id, author_name, has_series, description," +
                " illust_url_small, illust_url_original, illust_url_mini, illust_url_thumb," +
                " illust_url_regular, page_count, bookmark_count, like_count, view_count, grading, tags," +
                " create_time, update_time, patch_time, series_id, series_order, next_artwork_id," +
                " next_artwork_title, previous_artwork_id, previous_artwork_title)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "bookmark_count=VALUES(bookmark_count), " +
                "view_count=VALUES(view_count), " +
                "like_count=VALUES(like_count)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, artwork.getArtworkId());
        query.setParameter(2, artwork.getArtworkTitle());
        query.setParameter(3, artwork.getAuthorId());
        query.setParameter(4, artwork.getAuthorName());
        query.setParameter(5, artwork.isHasSeries());
        query.setParameter(6, artwork.getDescription());
        query.setParameter(7, artwork.getIllustUrlSmall());
        query.setParameter(8, artwork.getIllustUrlOriginal());
        query.setParameter(9, artwork.getIllustUrlMini());
        query.setParameter(10, artwork.getIllustUrlThumb());
        query.setParameter(11, artwork.getIllustUrlRegular());
        query.setParameter(12, artwork.getPageCount());
        query.setParameter(13, artwork.getBookmarkCount());
        query.setParameter(14, artwork.getLikeCount());
        query.setParameter(15, artwork.getViewCount());
        query.setParameter(16, artwork.getGrading());
        query.setParameter(17, artwork.getTags());
        query.setParameter(18, artwork.getCreateTime());
        query.setParameter(19, artwork.getUpdateTime());
        query.setParameter(20, artwork.getPatchTime());
        query.setParameter(21, artwork.getSeriesId());
        query.setParameter(22, artwork.getSeriesOrder());
        query.setParameter(23, artwork.getNextArtworkId());
        query.setParameter(24, artwork.getNextArtworkTitle());
        query.setParameter(25, artwork.getPreviousArtworkId());
        query.setParameter(26, artwork.getPreviousArtworkTitle());
        query.executeUpdate();
    }

    @Override
    public Artwork findArtwork(String tableName, int artworkId) {
        String sql = "select * from " + tableName + " where pk_artwork_id=?;";
        Query query = entityManager.createNativeQuery(sql, Artwork.class);
        query.setParameter(1, artworkId);
        return (Artwork) query.getSingleResult();
    }
}
