package net.mikoto.pixiv.database.pojo;

/**
 * @author mikoto
 * @date 2022/2/7 2:09
 */
public class IndexData {
    private IndexType indexType;
    private String tableName;
    private int size;
    private Integer smallest = 0;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getSmallest() {
        return smallest;
    }

    public void setSmallest(Integer smallest) {
        this.smallest = smallest;
    }

    public IndexType getIndexType() {
        return indexType;
    }

    public void setIndexType(IndexType indexType) {
        this.indexType = indexType;
    }
}
