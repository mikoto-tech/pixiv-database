package net.mikoto.pixiv.database.algorithm;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
public interface ShardingAlgorithm<T> {
    /**
     * Do sharding to the data by key.
     *
     * @param key         The key of the data.
     * @param tablePrefix The prefix of the table.
     * @param tableCount  The number of the table.
     * @return The table name
     */
    String doSharding(T key, String tablePrefix, int tableCount);
}
