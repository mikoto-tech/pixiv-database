package net.mikoto.pixiv.database.service;

/**
 * @author mikoto
 * @date 2022/5/7 16:45
 */
public enum OrderBy {
    /**
     * By time
     */
    ARTWORK_ID,

    /**
     * By bookmark count
     */
    BOOKMARK_COUNT,

    /**
     * By like count
     */
    LIKE_COUNT,

    /**
     * By view count
     */
    VIEW_COUNT
}
