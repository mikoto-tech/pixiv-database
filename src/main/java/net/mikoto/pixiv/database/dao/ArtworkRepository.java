package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.api.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * Get artworks by series id
     *
     * @param seriesId The series id.
     * @param pageable Page.
     * @return Artworks.
     */
    Page<Artwork> findArtworksBySeriesId(int seriesId, Pageable pageable);

    /**
     * Get artworks.
     *
     * @param tags     The tags need to find.
     * @param title    The title need to find.
     * @param pageable Page.
     * @return Artworks.
     */
    Page<Artwork> findArtworksByTagsContainsOrArtworkTitleContains(String tags, String title, Pageable pageable);
}
