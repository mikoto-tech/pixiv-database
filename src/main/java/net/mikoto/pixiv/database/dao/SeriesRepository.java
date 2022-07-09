package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.core.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * @date 2022/5/21 4:56
 */
@Repository("seriesRepository")
public interface SeriesRepository extends JpaRepository<Series, Integer> {
    /**
     * Get series by series id.
     *
     * @param seriesId The series id.
     * @return The series.
     */
    Series getSeriesBySeriesId(Integer seriesId);
}
