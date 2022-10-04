package net.mikoto.pixiv.database.algorithm.impl;

import net.mikoto.pixiv.database.algorithm.ShardingAlgorithm;
import org.springframework.stereotype.Component;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
@Component("IntegerShardingAlgorithm")
public class IntegerShardingAlgorithmImpl implements ShardingAlgorithm<Integer> {
    /**
     * Do sharding to the data by key.
     *
     * @param key         The key of the data.
     * @param tablePrefix The prefix of the table.
     * @param tableCount  The number of the table.
     * @return The table name
     */
    @Override
    public String doSharding(Integer key, String tablePrefix, int tableCount) {
        return tablePrefix + (key % tableCount);
    }
}
