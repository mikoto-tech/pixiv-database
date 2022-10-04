package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.database.model.TagArtworkIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
@Repository
public interface TagArtworkRepository extends JpaRepository<TagArtworkIndex, String>, TagArtworkCustomRepository {
}
