package net.mikoto.pixiv.database.service;


import net.mikoto.pixiv.api.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * @date 2022/4/17 20:36
 */
public interface ArtworkService {
    /**
     * Get an artwork by artwork id.
     *
     * @param artworkId The artwork's id.
     * @return The artwork.
     */
    Artwork getArtworkByArtworkId(Integer artworkId);

    /**
     * Insert an artwork
     *
     * @param artwork An artwork object.
     */
    void insertArtwork(Artwork artwork);

    /**
     * Get artworks.
     *
     * @param key      The credential.
     * @param pageable Page.
     * @return Artworks.
     */
    Page<Artwork> getArtworksByKey(String key, Pageable pageable) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * Get artworks.
     *
     * @param seriesId The credential.
     * @param pageable Page.
     * @return Artworks.
     */
    Page<Artwork> getArtworksBySeriesId(Integer seriesId, Pageable pageable) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
