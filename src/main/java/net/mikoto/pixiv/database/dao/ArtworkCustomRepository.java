package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.core.model.Artwork;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
public interface ArtworkCustomRepository {
    @Transactional
    @Modifying
    void save(String tableName, Artwork artwork);

    @Modifying
    Artwork findArtwork(String tableName, int artworkId);
}
