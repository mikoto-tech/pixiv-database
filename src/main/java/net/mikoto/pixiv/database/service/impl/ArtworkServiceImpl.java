package net.mikoto.pixiv.database.service.impl;

import net.mikoto.pixiv.database.dao.ArtworkRepository;
import net.mikoto.pixiv.database.pojo.Artwork;
import net.mikoto.pixiv.database.service.ArtworkService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author mikoto
 * @date 2022/4/17 20:37
 */
@Service("artworkService")
public class ArtworkServiceImpl implements ArtworkService {
    @Qualifier("artworkRepository")
    private final ArtworkRepository artworkRepository;

    public ArtworkServiceImpl(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    @Override
    public Artwork getArtworkByArtworkId(Integer artworkId) {
        return artworkRepository.getArtworkByArtworkId(artworkId);
    }

    @Override
    public void insertArtwork(Artwork artwork) {
        artworkRepository.save(artwork);
    }
}
