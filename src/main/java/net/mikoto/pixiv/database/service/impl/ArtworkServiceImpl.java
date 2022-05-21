package net.mikoto.pixiv.database.service.impl;

import net.mikoto.pixiv.api.model.Artwork;
import net.mikoto.pixiv.database.dao.ArtworkRepository;
import net.mikoto.pixiv.database.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * @date 2022/4/17 20:37
 */
@Service("artworkService")
public class ArtworkServiceImpl implements ArtworkService {
    @Qualifier("artworkRepository")
    private final ArtworkRepository artworkRepository;

    @Autowired
    public ArtworkServiceImpl(@NotNull ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    /**
     * Get artworks
     *
     * @return Artworks.
     */
    @Override
    public Page<Artwork> getArtworksByKey(String key, Pageable pageable) throws InvocationTargetException, IllegalAccessException {
        return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContains(key, key, pageable);
    }

    /**
     * Get artworks.
     *
     * @param seriesId The credential.
     * @param pageable Page.
     * @return Artworks.
     */
    @Override
    public Page<Artwork> getArtworksBySeriesId(Integer seriesId, Pageable pageable) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return artworkRepository.findArtworksBySeriesId(seriesId, pageable);
    }

    /**
     * Get an artwork by artwork id.
     *
     * @param artworkId The artwork's id.
     * @return The artwork.
     */
    @Override
    public Artwork getArtworkByArtworkId(Integer artworkId) {
        return artworkRepository.getArtworkByArtworkId(artworkId);
    }

    /**
     * Insert an artwork
     *
     * @param artwork An artwork object.
     */
    @Override
    public void insertArtwork(Artwork artwork) {
        artworkRepository.save(artwork);
    }
}
