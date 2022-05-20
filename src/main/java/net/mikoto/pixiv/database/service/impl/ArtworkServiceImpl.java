package net.mikoto.pixiv.database.service.impl;

import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.database.dao.ArtworkRepository;
import net.mikoto.pixiv.database.service.ArtworkService;
import net.mikoto.pixiv.database.service.FindBy;
import net.mikoto.pixiv.database.service.Order;
import net.mikoto.pixiv.database.service.OrderBy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
     * @return Artworks.
     */
    @Override
    public List<?> getArtworks(FindBy findBy, OrderBy orderBy, Order order, Object credential) throws InvocationTargetException, IllegalAccessException {
        if (credential == null && findBy == FindBy.Key) {
            credential = ";";
        }

        String methodName = "findArtworksBy";

        if (findBy == FindBy.Key) {
            methodName += "TagsContainsOrArtworkTitleContainsOrderBy";
        } else {
            methodName += findBy + "OrderBy";
        }

        methodName += orderBy.toString() + order.toString();

        Method method = null;
        for (Method aMethod :
                artworkRepository.getClass().getMethods()) {
            if (aMethod.getName().equals(methodName)) {
                method = aMethod;
            }
        }

        if (method == null) {
            throw new NullPointerException();
        }

        List<Object> paramList = new ArrayList<>();
        for (int i = 0; i < method.getParameterCount(); i++) {
            paramList.add(credential);
        }

        return (List<?>) method.invoke(artworkRepository, paramList.toArray());
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
