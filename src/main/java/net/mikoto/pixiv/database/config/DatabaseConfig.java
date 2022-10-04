package net.mikoto.pixiv.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mikoto
 * {@code @time} 2022/10/2
 * Create for pixiv-database
 */
@Configuration
@ConfigurationProperties(prefix = "mikoto.pixiv.database.table")
public class DatabaseConfig {
    private String artworkTablePrefix = "artwork_";
    private int artworkTableCount = 40;
    private String tagArtworkTablePrefix = "tag_artwork_index_";
    private int tagArtworkTableCount = 50;

    public String getArtworkTablePrefix() {
        return artworkTablePrefix;
    }

    public void setArtworkTablePrefix(String artworkTablePrefix) {
        this.artworkTablePrefix = artworkTablePrefix;
    }

    public int getArtworkTableCount() {
        return artworkTableCount;
    }

    public void setArtworkTableCount(int artworkTableCount) {
        this.artworkTableCount = artworkTableCount;
    }

    public String getTagArtworkTablePrefix() {
        return tagArtworkTablePrefix;
    }

    public void setTagArtworkTablePrefix(String tagArtworkTablePrefix) {
        this.tagArtworkTablePrefix = tagArtworkTablePrefix;
    }

    public int getTagArtworkTableCount() {
        return tagArtworkTableCount;
    }

    public void setTagArtworkTableCount(int tagArtworkTableCount) {
        this.tagArtworkTableCount = tagArtworkTableCount;
    }
}