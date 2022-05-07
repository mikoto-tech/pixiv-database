package net.mikoto.pixiv.database.service;


import net.mikoto.pixiv.api.pojo.Artwork;

import java.util.List;

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
     * Get artworks
     *
     * @param key     Key.
     * @param orderBy Order by.
     * @param order   Order.
     * @return Artworks.
     */
    List<Artwork> getArtworks(String key, OrderBy orderBy, Order order);

    /**
     * Get series artwork.
     *
     * @param seriesId Series id.
     * @return The artworks.
     */
    List<Artwork> getSeries(int seriesId);
}
