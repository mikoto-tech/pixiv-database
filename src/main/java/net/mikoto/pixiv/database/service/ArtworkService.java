package net.mikoto.pixiv.database.service;


import net.mikoto.pixiv.api.pojo.Artwork;

import java.lang.reflect.InvocationTargetException;
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
     * Get artworks.
     *
     * @param findBy     Find by.
     * @param orderBy    Order by.
     * @param order      Order.
     * @param credential The credential.
     * @return Artworks.
     */
    List<?> getArtworks(FindBy findBy, OrderBy orderBy, Order order, Object credential) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
