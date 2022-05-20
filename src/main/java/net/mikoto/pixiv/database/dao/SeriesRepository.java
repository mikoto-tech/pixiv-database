package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.api.pojo.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * @date 2022/5/21 4:56
 */
@Repository("seriesRepository")
public interface SeriesRepository extends JpaRepository<Series, Integer> {
}
