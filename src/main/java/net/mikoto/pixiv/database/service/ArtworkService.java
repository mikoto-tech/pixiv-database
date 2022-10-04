package net.mikoto.pixiv.database.service;

import net.mikoto.pixiv.core.model.Artwork;

import java.util.List;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
public interface ArtworkService {
    void insertArtwork(Artwork artwork);

    List<Artwork> getArtworks(String tag, int grading, String properties, int pageSize, int pageCount);

    Artwork getArtwork(int artworkId);
}
