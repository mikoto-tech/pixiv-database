package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.core.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * {@code @time} 2022/10/2
 * Create for pixiv-database
 */
@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Integer>, ArtworkCustomRepository {
}
