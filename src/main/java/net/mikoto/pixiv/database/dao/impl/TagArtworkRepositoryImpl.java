package net.mikoto.pixiv.database.dao.impl;

import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.database.dao.TagArtworkCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
public class TagArtworkRepositoryImpl implements TagArtworkCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(String tableName, String tag, Artwork artwork) {
        // 插入主表
        String sql = "INSERT INTO " + tableName +
                " (tag, grading, bookmark_count, like_count, view_count, artwork_id)" +
                "VALUES (?, ?, ?, ?, ?, ?) ";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, tag);
        query.setParameter(2, artwork.getGrading());
        query.setParameter(3, artwork.getBookmarkCount());
        query.setParameter(4, artwork.getLikeCount());
        query.setParameter(5, artwork.getViewCount());
        query.setParameter(6, artwork.getArtworkId());
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findArtworks(String tag, int grading, String table, String properties, int pageSize, int pageCount) {
        String sql = "select distinct artwork_id from " +
                table +
                " where grading <= " + grading +
                " and tag like '" + tag + "'" +
                " order by " + properties + "" +
                " desc limit " + (pageCount - 1) * pageSize + "," + pageCount * pageSize + ";";
        // 检测是否为随机TAG
        if (tag.contains("RANDOM_TAG")) {
            sql = sql.replace("and tag like '" + tag + "' ", "");
        }
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
}
