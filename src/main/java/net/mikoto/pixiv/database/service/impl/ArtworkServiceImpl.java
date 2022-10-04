package net.mikoto.pixiv.database.service.impl;

import cn.hutool.core.util.RandomUtil;
import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.database.algorithm.ShardingAlgorithm;
import net.mikoto.pixiv.database.config.DatabaseConfig;
import net.mikoto.pixiv.database.dao.ArtworkRepository;
import net.mikoto.pixiv.database.dao.TagArtworkRepository;
import net.mikoto.pixiv.database.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
@Service
public class ArtworkServiceImpl implements ArtworkService {
    private final ArtworkRepository artworkRepository;
    private final TagArtworkRepository tagArtworkRepository;
    @Qualifier("IntegerShardingAlgorithm")
    private final ShardingAlgorithm<Integer> artworkShardingAlgorithm;
    @Qualifier("StringShardingAlgorithm")
    private final ShardingAlgorithm<String> indexShardingAlgorithm;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository artworkRepository, TagArtworkRepository tagArtworkRepository, ShardingAlgorithm<Integer> artworkShardingAlgorithm, ShardingAlgorithm<String> indexShardingAlgorithm, DatabaseConfig databaseConfig) {
        this.artworkRepository = artworkRepository;
        this.tagArtworkRepository = tagArtworkRepository;
        this.artworkShardingAlgorithm = artworkShardingAlgorithm;
        this.indexShardingAlgorithm = indexShardingAlgorithm;
        this.databaseConfig = databaseConfig;
    }

    @Override
    public void insertArtwork(Artwork artwork) {
        // 插入图片主要数据进入主表artwork
        artworkRepository.save(
                // 通过分片算法获取表名
                artworkShardingAlgorithm.doSharding(
                        artwork.getArtworkId(),
                        databaseConfig.getArtworkTablePrefix(),
                        databaseConfig.getArtworkTableCount()
                ),
                artwork
        );

        for (String tag :
                artwork.getTags().split(";")) {
            tagArtworkRepository.save(
                    indexShardingAlgorithm.doSharding(
                            tag,
                            databaseConfig.getTagArtworkTablePrefix(),
                            databaseConfig.getTagArtworkTableCount()
                    ), tag, artwork);
        }
    }

    @Override
    public List<Artwork> getArtworks(@NotNull String tag, int grading, String properties, int pageSize, int pageCount) {
        if (tag.contains("RANDOM_TAG")) {
            tag = "RANDOM_TAG|LOVE YOU FOREVER, Lin.|" + RandomUtil.randomString(10);
        }
        List<Integer> tagArtworkIndexPage =
                tagArtworkRepository.findArtworks(
                        tag,
                        grading,
                        indexShardingAlgorithm.doSharding(
                                tag,
                                databaseConfig.getTagArtworkTablePrefix(),
                                databaseConfig.getTagArtworkTableCount()
                        ), properties, pageSize, pageCount);
        List<Artwork> artworks = new ArrayList<>();
        for (Integer singleArtworkId : tagArtworkIndexPage) {
            artworks.add(artworkRepository.findArtwork(
                    artworkShardingAlgorithm.doSharding(
                            singleArtworkId,
                            databaseConfig.getArtworkTablePrefix(),
                            databaseConfig.getArtworkTableCount()
                    ),
                    singleArtworkId
            ));
        }
        return artworks;
    }

    @Override
    public Artwork getArtwork(int artworkId) {
        Optional<Artwork> artwork = artworkRepository.findById(artworkId);
        return artwork.orElse(null);
    }
}
