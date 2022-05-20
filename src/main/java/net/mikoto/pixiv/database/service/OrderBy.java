package net.mikoto.pixiv.database.service;

/**
 * @author mikoto
 * @date 2022/5/7 16:45
 */
public enum OrderBy {
    /**
     * By time
     */
    ArtworkId,

    /**
     * By bookmark count
     */
    BookmarkCount,

    /**
     * By like count
     */
    LikeCount,

    /**
     * By view count
     */
    ViewCount,
    SeriesOrder
}
