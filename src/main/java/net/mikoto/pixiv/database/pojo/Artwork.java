package net.mikoto.pixiv.database.pojo;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author mikoto
 * Created at 21:30:24, 2021/9/19
 */
@Entity
@Table(name = "artwork")
public class Artwork {
    @Id
    @Column(name = "pk_artwork_id", nullable = false, unique = true)
    private int artworkId;
    @Column(name = "artwork_title", nullable = false)
    private String artworkTitle;
    @Column(name = "author_id", nullable = false)
    private int authorId;
    @Column(name = "author_name", nullable = false)
    private String authorName;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "illust_url_small", nullable = false)
    private String illustUrlSmall;
    @Column(name = "illust_url_Original", nullable = false)
    private String illustUrlOriginal;
    @Column(name = "illust_url_mini", nullable = false)
    private String illustUrlMini;
    @Column(name = "illust_url_Thumb", nullable = false)
    private String illustUrlThumb;
    @Column(name = "illust_url_Regular", nullable = false)
    private String illustUrlRegular;
    @Column(name = "page_count", nullable = false)
    private int pageCount;
    @Column(name = "bookmark_count", nullable = false)
    private int bookmarkCount;
    @Column(name = "like_count", nullable = false)
    private int likeCount;
    @Column(name = "view_count", nullable = false)
    private int viewCount;
    @Column(name = "grading", nullable = false)
    private int grading;
    @Column(name = "tags", nullable = false)
    private String tags;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "update_time", nullable = false)
    private Date updateTime;
    @Column(name = "patch_time", nullable = false)
    private Date patchTime;

    /**
     * Load pixiv data from json
     *
     * @param jsonObject Json object
     * @return Pixiv data
     */
    public Artwork loadJson(@NotNull JSONObject jsonObject) throws IllegalAccessException {
        for (Field field :
                this.getClass().getDeclaredFields()) {
            field.set(this, jsonObject.get(field.getName()));
        }
        return this;
    }

    public JSONObject toJsonObject() throws IllegalAccessException {
        JSONObject outputJsonObject = new JSONObject();

        for (Field field :
                this.getClass().getDeclaredFields()) {
            outputJsonObject.put(field.getName(), field.get(this));
        }

        return outputJsonObject;
    }

    public int getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public void setArtworkTitle(String artworkTitle) {
        this.artworkTitle = artworkTitle;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustUrlSmall() {
        return illustUrlSmall;
    }

    public void setIllustUrlSmall(String illustUrlSmall) {
        this.illustUrlSmall = illustUrlSmall;
    }

    public String getIllustUrlOriginal() {
        return illustUrlOriginal;
    }

    public void setIllustUrlOriginal(String illustUrlOriginal) {
        this.illustUrlOriginal = illustUrlOriginal;
    }

    public String getIllustUrlMini() {
        return illustUrlMini;
    }

    public void setIllustUrlMini(String illustUrlMini) {
        this.illustUrlMini = illustUrlMini;
    }

    public String getIllustUrlThumb() {
        return illustUrlThumb;
    }

    public void setIllustUrlThumb(String illustUrlThumb) {
        this.illustUrlThumb = illustUrlThumb;
    }

    public String getIllustUrlRegular() {
        return illustUrlRegular;
    }

    public void setIllustUrlRegular(String illustUrlRegular) {
        this.illustUrlRegular = illustUrlRegular;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getGrading() {
        return grading;
    }

    public void setGrading(int grading) {
        this.grading = grading;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPatchTime() {
        return patchTime;
    }

    public void setPatchTime(Date patchTime) {
        this.patchTime = patchTime;
    }
}

