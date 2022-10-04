package net.mikoto.pixiv.database.algorithm.impl;

import cn.hutool.crypto.SecureUtil;
import net.mikoto.pixiv.database.algorithm.ShardingAlgorithm;
import org.springframework.stereotype.Component;

/**
 * @author mikoto
 * {@code @time} 2022/10/3
 * Create for pixiv-database
 */
@Component("StringShardingAlgorithm")
public class StringShardingAlgorithmImpl implements ShardingAlgorithm<String> {
    /**
     * Do sharding to the data by key.
     *
     * @param key         The key of the data.
     * @param tablePrefix The prefix of the table.
     * @param tableCount  The number of the table.
     * @return The table name
     */
    @Override
    public String doSharding(String key, String tablePrefix, int tableCount) {
        String md5 = SecureUtil.md5(key);
        int result = 0;
        for (char md5Char :
                md5.toCharArray()) {
            result += md5Char;
        }
        return tablePrefix + (result % tableCount);
    }
}
