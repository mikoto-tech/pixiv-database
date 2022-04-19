package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.database.pojo.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * @date 2022/4/17 20:24
 */
@Repository("artworkRepository")
public interface ArtworkRepository extends JpaRepository<Artwork, Integer> {
    /**
     * Get an artwork by artwork id.
     *
     * @param artworkId The artwork id.
     * @return An artwork.
     */
    Artwork getArtworkByArtworkId(Integer artworkId);
}
