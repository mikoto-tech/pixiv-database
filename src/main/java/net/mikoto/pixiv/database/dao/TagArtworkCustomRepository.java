package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.core.model.Artwork;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
public interface TagArtworkCustomRepository {
    @Transactional
    @Modifying
    void save(String tableName, String tag, Artwork artwork);

    @Modifying
    List<Integer> findArtworks(String tag, int grading, String table, String properties, int pageSize, int pageCount);
}
