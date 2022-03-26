package net.mikoto.pixiv.database.service.impl;

import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.api.pojo.IndexType;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import net.mikoto.pixiv.database.service.PixivDataService;

import java.sql.SQLException;

import static net.mikoto.pixiv.database.PixivDatabaseApplication.ARTWORK_DAO;

/**
 * @author mikoto
 * @date 2022/2/7 1:36
 */
public class ArtworkServiceImpl implements PixivDataService {
    /**
     * Insert a pixiv data.
     *
     * @param artwork The artwork data
     * @throws SQLException                Exception.
     * @throws UnknownServiceTypeException Exception.
     */
    @Override
    public synchronized void insertArtwork(Artwork artwork) throws SQLException, UnknownServiceTypeException {
        ARTWORK_DAO.insertArtwork(artwork);
        ARTWORK_DAO.insertIndex(IndexType.bookmark_count, artwork);
        ARTWORK_DAO.insertIndex(IndexType.like_count, artwork);
        ARTWORK_DAO.insertIndex(IndexType.view_count, artwork);
    }
}
