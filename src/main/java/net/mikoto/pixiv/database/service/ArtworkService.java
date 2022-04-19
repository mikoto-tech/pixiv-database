package net.mikoto.pixiv.database.service;


import net.mikoto.pixiv.database.pojo.Artwork;

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
}
