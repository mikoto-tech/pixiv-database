package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.core.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mikoto
 * {@code @time} 2022/10/2
 * Create for pixiv-database
 */
public interface ArtworkRepository extends JpaRepository<Artwork, Integer> {
}
