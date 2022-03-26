package net.mikoto.pixiv.database.service;

import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;

import java.sql.SQLException;

/**
 * @author mikoto
 * @date 2022/3/12 1:26
 */
public interface PixivDataService {
    /**
     * Insert a pixiv data.
     *
     * @param artwork The artwork data
     * @throws SQLException                Exception.
     * @throws UnknownServiceTypeException Exception.
     */
    void insertArtwork(Artwork artwork) throws SQLException, UnknownServiceTypeException;
}
