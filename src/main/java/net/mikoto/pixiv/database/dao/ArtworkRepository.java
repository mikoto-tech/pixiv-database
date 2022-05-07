package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.api.pojo.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


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
     * @return Artworks.
     */
    List<Artwork> findArtworksBySeriesIdOrderBySeriesOrderDesc(int seriesId);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByBookmarkCountDesc(String tags, String title);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByBookmarkCountAsc(String tags, String title);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByLikeCountDesc(String tags, String title);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByLikeCountAsc(String tags, String title);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByViewCountDesc(String tags, String title);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByViewCountAsc(String tags, String title);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByArtworkIdAsc(String tags, String title);

    /**
     * Get artworks.
     *
     * @param tags  The tags need to find.
     * @param title The title need to find.
     * @return Artworks.
     */
    List<Artwork> findArtworksByTagsContainsOrArtworkTitleContainsOrderByArtworkIdDesc(String tags, String title);
}
