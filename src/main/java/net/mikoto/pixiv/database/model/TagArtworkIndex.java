package net.mikoto.pixiv.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
@Entity
@Table(
        name = "tag_artwork_index"
)
public class TagArtworkIndex {
    @Id
    @Column(
            name = "tag"
    )
    private String tag;
    private int grading;
    private int bookmark_count;
    private int like_count;
    private int view_count;
    private int artworkId;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getGrading() {
        return grading;
    }

    public void setGrading(int grading) {
        this.grading = grading;
    }

    public int getBookmark_count() {
        return bookmark_count;
    }

    public void setBookmark_count(int bookmark_count) {
        this.bookmark_count = bookmark_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagArtworkIndex that = (TagArtworkIndex) o;
        return grading == that.grading && bookmark_count == that.bookmark_count && like_count == that.like_count && view_count == that.view_count && artworkId == that.artworkId && tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, grading, bookmark_count, like_count, view_count, artworkId);
    }

    @Override
    public String toString() {
        return "TagArtworkIndex{" +
                "tag='" + tag + '\'' +
                ", grading=" + grading +
                ", bookmark_count=" + bookmark_count +
                ", like_count=" + like_count +
                ", view_count=" + view_count +
                ", artworkId=" + artworkId +
                '}';
    }
}
