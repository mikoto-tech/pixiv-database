package net.mikoto.pixiv.database.service;

import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import net.mikoto.pixiv.database.pojo.IndexType;
import net.mikoto.pixiv.database.pojo.PixivData;

import java.sql.SQLException;

import static net.mikoto.pixiv.database.PixivDatabaseApplication.PIXIV_DATA_DAO;

/**
 * @author mikoto
 * @date 2022/2/7 1:36
 */
public class PixivDataService {
    private static final PixivDataService INSTANCE = new PixivDataService();

    public static PixivDataService getInstance() {
        return INSTANCE;
    }

    public synchronized void addPixivData(PixivData pixivData) throws SQLException, UnknownServiceTypeException {
        PIXIV_DATA_DAO.insertPixivData(pixivData);
        PIXIV_DATA_DAO.insertIndex(IndexType.bookmark_count, pixivData);
        PIXIV_DATA_DAO.insertIndex(IndexType.like_count, pixivData);
        PIXIV_DATA_DAO.insertIndex(IndexType.view_count, pixivData);
    }
}
