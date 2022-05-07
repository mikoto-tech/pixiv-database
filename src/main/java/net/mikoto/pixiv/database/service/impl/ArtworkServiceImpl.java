package net.mikoto.pixiv.database.service.impl;

import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.database.dao.ArtworkRepository;
import net.mikoto.pixiv.database.service.ArtworkService;
import net.mikoto.pixiv.database.service.Order;
import net.mikoto.pixiv.database.service.OrderBy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @param key     Key.
     * @param orderBy Order by.
     * @param order   Order.
     * @return Artworks.
     */
    @Override
    public List<Artwork> getArtworks(String key, OrderBy orderBy, Order order) {
        if (key == null) {
            key = ";";
        }

        if (order.equals(Order.DESC)) {
            switch (orderBy) {
                case BOOKMARK_COUNT -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByBookmarkCountDesc(key, key);
                }
                case ARTWORK_ID -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByArtworkIdDesc(key, key);
                }
                case LIKE_COUNT -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByLikeCountDesc(key, key);
                }
                case VIEW_COUNT -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByViewCountDesc(key, key);
                }
                default -> {
                    throw new UnsupportedOperationException();
                }
            }
        } else if (order.equals(Order.ASC)) {
            switch (orderBy) {
                case BOOKMARK_COUNT -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByBookmarkCountAsc(key, key);
                }
                case ARTWORK_ID -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByArtworkIdAsc(key, key);
                }
                case LIKE_COUNT -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByLikeCountAsc(key, key);
                }
                case VIEW_COUNT -> {
                    return artworkRepository.findArtworksByTagsContainsOrArtworkTitleContainsOrderByViewCountAsc(key, key);
                }
                default -> {
                    throw new UnsupportedOperationException();
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Get series artwork.
     *
     * @param seriesId Series id.
     * @return The artworks.
     */
    @Override
    public List<Artwork> getSeries(int seriesId) {
        return artworkRepository.findArtworksBySeriesIdOrderBySeriesOrderDesc(seriesId);
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
